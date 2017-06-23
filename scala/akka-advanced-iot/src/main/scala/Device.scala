package cn.enali.example.iot

import akka.actor.{Actor, ActorLogging, Props}

object Device {
  def props(groupId: String, deviceId: String) = Props(new Device(groupId, deviceId))

  final case class ReadTemperature(requestId: Long)  // 请求读温度的消息
  final case class RespondTemperature(requestId: Long, value: Option[Double])  // 回复当前温度

  final case class RecordTemperature(requestId: Long, value: Double)  // 请求记录温度的消息
  final case class TemperatureRecorded(requestId: Long)  // 因为akka不保证消息一定能收到, 所以有必要回复发送者消息是否被处理
}

class Device(groupId: String, deviceId: String) extends Actor with ActorLogging {
  import Device._
  import DeviceManager._

  var lastTemperatureReading: Option[Double] = None

  override def preStart(): Unit = log.info("Device actor {}-{} started", groupId, deviceId)

  override def postStop(): Unit = log.info("Device actor {}-{} stopped", groupId, deviceId)

  override def receive: Receive = {
    // 经DeviceManager - DeviceGroup转发的注册device消息, 因为是转发, 所以sender仍然指向最初的发送者
    case RequestTrackDevice(`groupId`, `deviceId`) =>  // 此处并非提取值到变量, 而是完整匹配, `var`意为取变量var的值, 而非变量
      sender ! DeviceRegistered

    case RequestTrackDevice(groupId, deviceId) =>  // 因为在第一个case之后, 所以此时的注册信息一定是不匹配当前device的, 包括groupId, deviceId
      log.warning(  // 简单忽视, 什么也不做
        "Ignoring TrackDevice request for {}-{}. This actor is responsible for {}-{}",
        groupId, deviceId, this.groupId, this.deviceId
      )

    case RecordTemperature(id, value) =>  // 让device记录温度的消息
      log.info("Recorded temperature reading {} with {}", value, id)
      lastTemperatureReading = Some(value)  // 更改内部状态变量
      sender ! TemperatureRecorded(id)  // 回复已成功记录温度

    case ReadTemperature(id) =>  // 请求读温度的消息
      sender ! RespondTemperature(id, lastTemperatureReading)  // 回复当前的温度, 可能是Some(value)也可能是None
  }
}
