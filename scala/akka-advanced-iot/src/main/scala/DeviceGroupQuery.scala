package cn.enali.example.iot

import akka.actor.{Actor, ActorLogging, ActorRef, Props, Terminated}

import scala.concurrent.duration.FiniteDuration

/**
  * Created by enali on 2017/6/23.
  */
object DeviceGroupQuery {
  case object CollectionTimeout  // 有一个特殊的定时发送消息, 即在指定 时间后发送此消息给device group actor, 表示到时间了

  def props(
    actorToDeviceId: Map[ActorRef, String],
    requestId: Long,
    requester: ActorRef,
    timeout: FiniteDuration
  ): Props = {
    Props(new DeviceGroupQuery(actorToDeviceId, requestId, requester, timeout))
  }
}

// 通过将只跟查询本身有关的临时状态保存在分开的Actor中, 我们保持了DeviceGroup实现的简单性.
// 它将所有事情代理给子Actor, 因此不必保存那些与其核心事务不相关的状态.
class DeviceGroupQuery(  // 这是唯一的不对应具体实体的Actor, 一个抽象的Actor
  actorToDeviceId: Map[ActorRef, String],
  requestId: Long,  // 此requestId表示发送者的ID, 在收集完所有温度信息后, 再回复发送者
  requester: ActorRef,
  timeout: FiniteDuration
) extends Actor with ActorLogging{

  import DeviceGroupQuery._
  import context.dispatcher  // 导入scheduleOne执行需要的隐式参数ExecutionContext

  val queryTimeoutTimer = context.system.scheduler.scheduleOnce(timeout, self, CollectionTimeout)  // 当超时时向自己发送CollectionTimeout的消息

  override def preStart(): Unit = {  // 在实例化此actor时, 向当前group所有的device请求读温度信息
    actorToDeviceId.keysIterator.foreach { deviceActor =>
      context.watch(deviceActor)  // 监视所有deviceActor, 当其stop时, 可以立即处理, 而不用等待超时
      deviceActor ! Device.ReadTemperature(0)  // 向所有deviceActor请求温度信息, 这里默认以0发送请求温度信息
    }
  }

  override def postStop(): Unit = {
    queryTimeoutTimer.cancel()  // 当actor停止时, 取消超时计时, 可能是由超时引起, 也可能是由处理完全部device引起
  }

  // 并非receive函数在处理请求, 而是receive函数返回的Receive(其本质上是偏函数)在处理请求
  override def receive: Receive =  // 向receive代理给返回Receive的函数, Receive本质上是PartialFunction[Any, Unit], 偏函数
    waitingForReplies(
      Map.empty,
      actorToDeviceId.keySet
    )

  // 返回Receive函数来处理请求, 若改变waitingForReplies的参数, 则返回不同的Receive函数
  // 因为actor每次只处理一个消息, 因此每次只调用一个receivedResponse, 不会引发竞争
  def waitingForReplies(
    repliesSoFar: Map[String, DeviceGroup.TemperatureReading],
    stillWaiting: Set[ActorRef]
  ): Receive = {
    case Device.RespondTemperature(0, valueOption) =>  // device actor正常返回温度信息
      val deviceActor = sender
      val reading = valueOption match {  // 根据是否返回了温度值, 返回不同的读信息
        case Some(value) => DeviceGroup.Temperature(value)
        case None => DeviceGroup.TemperatureNotAvailable
      }
      receivedResponse(deviceActor, reading, stillWaiting, repliesSoFar)  // 更改已回复/仍然在等待的device列表

    case Terminated(deviceActor) =>  // 收到device stop的信息
      receivedResponse(deviceActor, DeviceGroup.DeviceNotAvailable, stillWaiting, repliesSoFar)

    case CollectionTimeout =>  // 超时
      val timedOutReplies =  // 为仍然等待的actor设置为DeviceTimeout
        stillWaiting.map { deviceActor =>
          val deviceId = actorToDeviceId(deviceActor)
          deviceId -> DeviceGroup.DeviceTimeout
        }
      requester ! DeviceGroup.RespondAllTemperatures(requestId, repliesSoFar ++ timedOutReplies)  // 向请求者发送所有的温度信息
      context.stop(self)  // 停止query actor
  }

  def receivedResponse(
    deviceActor: ActorRef,
    reading: DeviceGroup.TemperatureReading,
    stillWaiting: Set[ActorRef],
    repliesSoFar: Map[String, DeviceGroup.TemperatureReading]
  ): Unit = {
    // 会调用此方法, 或者收到了响应, 或者device-actor停止, 收到了Terminated消息
    // 问题: 如果device actor前脚回了响应, 后脚给stop了, query actor仍然会继收到正常响应后又收到Terminated, 调用两次receivedResponse
    // 这会带来问题, 方法是凡调用此方法, 都unwatch, 这样Terminated就无效了, 且unwatch可调用多次, 只第一次有效
    context.unwatch(deviceActor)

    val deviceId = actorToDeviceId(deviceActor)
    val newStillWaiting = stillWaiting - deviceActor

    val newRepliesSoFar = repliesSoFar + (deviceId -> reading)
    if (newStillWaiting.isEmpty) {  // 如果等待处理的actor为空
      requester ! DeviceGroup.RespondAllTemperatures(requestId, newRepliesSoFar)  // 返回查询到的所有响应
      context.stop(self)  // 停止此query actor
    } else {
      context.become(waitingForReplies(newRepliesSoFar, newStillWaiting))  // 如果还有未处理的device, 则设置处理函数为新的waitingForReplies
    }
  }
}
