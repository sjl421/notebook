package cn.enali.example.iot

import akka.actor.ActorSystem
import akka.testkit.{TestKit, TestProbe}
import org.scalatest.{BeforeAndAfterAll, FlatSpecLike, Matchers}
import scala.concurrent.duration._

class DeviceSpec(_system: ActorSystem)
  extends TestKit(_system)  // 此处继承了 { implicit val system = _system }
    with Matchers
    with FlatSpecLike
    with BeforeAndAfterAll {

  def this() = this(ActorSystem("device-spec"))  // 辅助构造函数

  override def afterAll(): Unit = shutdown(system)  // shutdown是TestKitBase中定义的

  // 如果没有记录温度, 回复None
  "DeviceActor" should "reply with empty reading if no temperature is known" in {
    val probe = TestProbe()
    val deviceActor = system.actorOf(Device.props("group", "device"))

    deviceActor.tell(Device.ReadTemperature(requestId = 42), probe.ref)  // 相当于 probe.ref发送msg给deviceActor
    val response = probe.expectMsgType[Device.RespondTemperature]
    response.requestId should be (42)
    response.value should be (None)
  }

  // 若多次记录温度, 返回最新的
  "DeviceActor" should "reply with latest temperature reading" in {
    val probe = TestProbe()
    val deviceActor = system.actorOf(Device.props("group", "device"))

    deviceActor.tell(Device.RecordTemperature(requestId = 1, 24.0), probe.ref)
    probe.expectMsg(Device.TemperatureRecorded(requestId = 1))

    deviceActor.tell(Device.ReadTemperature(requestId = 2), probe.ref)
    val response1 = probe.expectMsgType[Device.RespondTemperature]
    response1.requestId should be (2)
    response1.value should be (Some(24.0))

    deviceActor.tell(Device.RecordTemperature(requestId = 3, 55.4), probe.ref)
    probe.expectMsg(Device.TemperatureRecorded(requestId = 3))

    deviceActor.tell(Device.ReadTemperature(requestId = 4), probe.ref)
    val response2 = probe.expectMsgType[Device.RespondTemperature]
    response2.requestId should be (4)
    response2.value should be (Some(55.4))
  }

  // 回复注册请求
  "DeviceActor" should "reply to registration requests" in {
    val probe = TestProbe()
    val deviceActor = system.actorOf(Device.props("group", "device"))

    deviceActor.tell(DeviceManager.RequestTrackDevice("group", "device"), probe.ref)
    probe.expectMsg(DeviceManager.DeviceRegistered)
    probe.lastSender should be (deviceActor)  // TODO 感觉lastSender应该是消息最后接收者的ActorRef
  }

  // 忽略错误的注册信息, 要么groupId不对, 要么deviceId不对
  "DeviceActor" should "ignore wrong registration requests" in {
    val probe = TestProbe()
    val deviceActor = system.actorOf(Device.props("group", "device"))

    deviceActor.tell(DeviceManager.RequestTrackDevice("wrongGroup", "device"), probe.ref)
    probe.expectNoMsg(500.milliseconds)  // 在超时后不会收到消息

    deviceActor.tell(DeviceManager.RequestTrackDevice("group", "wrongDevice"), probe.ref)
    probe.expectNoMsg(500.milliseconds)
  }
}
