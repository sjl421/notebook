package cn.enali.example.iot

import akka.actor.{Actor, ActorLogging, ActorRef, Props, Terminated}
import scala.concurrent.duration._

object DeviceGroup {
  def props(groupId: String) = Props(new DeviceGroup(groupId))

  final case class RequestDeviceList(requestId: Long)  // 请求当前group的device列表
  final case class ReplyDeviceList(requestId: Long, ids: Set[String])  // 收到的当前group的device列表信息

  final case class RequestAllTemperatures(requestId: Long)  // 请求设备组所有设备的温度
  final case class RespondAllTemperatures(requestId: Long, temperatures: Map[String, TemperatureReading])  // 返回的响应

  sealed trait TemperatureReading  // 请求温度可能面临的4种情况
  final case class Temperature(value: Double) extends TemperatureReading  // 正常返回温度
  case object TemperatureNotAvailable extends TemperatureReading  // 温度不可读
  case object DeviceNotAvailable extends TemperatureReading  // 设备不可见, 可能stop了
  case object DeviceTimeout extends TemperatureReading  // 超时未返回响应
}

// device group以groupId区分
class DeviceGroup(groupId: String) extends Actor with ActorLogging{ // 混入ActorLogging, 可使用log方法
  import DeviceGroup._
  import DeviceManager._

  var deviceIdToActor = Map.empty[String, ActorRef]  // deviceId到deviceActor的映射, 保存当前group的注册device信息
  var actorToDeviceId = Map.empty[ActorRef, String]  // deviceActor到deviceId的映射, 因为watch关系带来的Terminated消息只携带ActorRef信息, 所以需要一个反向映射

  override def preStart(): Unit = log.info("DeviceGroup {} started", groupId)

  override def postStop(): Unit = log.info("DeviceGroup {} stopped", groupId)

  override def receive: Receive = {
    case trackMsg @ RequestTrackDevice(`groupId`, _) =>  // 与本组的ID匹配
      deviceIdToActor.get(trackMsg.deviceId) match {  // 映射中存在/不存在, 使用匹配
        case Some(deviceActor) =>  // 此device已注册
          deviceActor forward trackMsg  // 转发消息到deviceActor, 转发会保留消息trackMsg的原发送者
        case None =>  // 此device未注册
          log.info("Creating device actor for {}", trackMsg.deviceId)
          val deviceActor = context.actorOf(Device.props(groupId, trackMsg.deviceId), s"device-${trackMsg.deviceId}")  // device actor不存在则实例化一个新的
          context.watch(deviceActor)  // 监视deviceActor, 被监视者stop后, 监视者会收到Terminated(ref)消息, ref是被监视者, 监视关系可在任意Actor之间建立
          actorToDeviceId += deviceActor -> trackMsg.deviceId  // 添加到两个映射中
          deviceIdToActor += trackMsg.deviceId -> deviceActor
          deviceActor forward trackMsg  // 创建新device actor实例后, 转发trackMsg消息
      }

    case RequestTrackDevice(groudId, deviceId) =>  // 请求注册device, 但其groupId不符合
      log.warning(
        "Ignoring TrackDevice request for {}. This actor is response for {}.",
        groupId, this.groupId
      )

    case RequestDeviceList(requestId) =>  // 请求当前device-group所注册的所有deviceId
      sender ! ReplyDeviceList(requestId, deviceIdToActor.keySet)  // 回复已注册的device集合

    case Terminated(deviceActor) =>  // 当device被stop后, device-group会收到此消息, 主要从注册映射中去掉相应的deviceId
      val deviceId = actorToDeviceId(deviceActor)  // 通过反向映射取出对应的devicdId
      log.info("Device actor for {} has been terminated", deviceId)
      actorToDeviceId -= deviceActor  // 取消注册
      deviceIdToActor -= deviceId

      // 多并发查询
    case RequestAllTemperatures(requestId) =>  // 若请求所有device的温度, 则实例化一个DeviceGroupQuery的Actor
      context.actorOf(DeviceGroupQuery.props(
        actorToDeviceId = actorToDeviceId,
        requestId = requestId,  // DeviceGroup收到RequestAllTemperatures的消息, 此处ID是发送者给DeviceGroup的ID
        requester = sender(),
        3.seconds
      ))
  }
}
