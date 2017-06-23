package cn.enali.example.iot

import akka.actor.{Actor, ActorLogging, ActorRef, Props, Terminated}


object DeviceManager {
  def props = Props[DeviceManager]

  // 向DeviceManager发送请求注册device的信息, 其会转发给相应组和设备;
  // 若相应组不存在, 则创建后再转发给组; 对于组而言, 若相应设备不存在, 则创建后再转发给设备
  final case class RequestTrackDevice(groupId: String, deviceId: String)
  case object DeviceRegistered  // 设备已注册信息, 由最终的设备回复
}

class DeviceManager extends Actor with ActorLogging {
  import DeviceManager._

  var groupIdToActor = Map.empty[String, ActorRef]  // groupId到groupActor的映射
  var actorToGroupId = Map.empty[ActorRef, String]  // groupActor到groupId的映射

  override def preStart(): Unit = log.info("DeviceManager started")

  override def postStop(): Unit = log.info("DeviceManager stopped")

  override def receive: Receive = {
    case trackMsg @ RequestTrackDevice(groupId, _) =>
      groupIdToActor.get(groupId) match {
        case Some(groupActor) =>  // 组已经存在
          groupActor forward trackMsg
        case None =>
          log.info("Creating device group actor for {}", groupId)
          val groupActor = context.actorOf(DeviceGroup.props(groupId), "group-" + groupId)
          context.watch(groupActor)  // 由groupManager监视groupActor
          groupActor forward trackMsg  // 转发消息给新创建的组
          groupIdToActor += groupId -> groupActor
          actorToGroupId += groupActor -> groupId
      }

    case Terminated(groupActor) =>  // 组停止的消息
      val groupId = actorToGroupId(groupActor)
      log.info("Device group actor for {} has been terminated", groupId)
      actorToGroupId -= groupActor
      groupIdToActor -= groupId
  }
}
