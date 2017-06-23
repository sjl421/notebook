package cn.enali.example.iot

import akka.actor.{ActorSystem, PoisonPill}
import akka.testkit.{TestKit, TestProbe}
import org.scalatest.{BeforeAndAfterAll, FlatSpecLike, Matchers}

import scala.concurrent.duration._  // 主要用于各种3.seconds

/**
  * Created by enali on 2017/6/23.
  */
class DeviceGroupSpec(_system: ActorSystem)
  extends TestKit(_system)
    with Matchers
    with FlatSpecLike
    with BeforeAndAfterAll {

  def this() = this(ActorSystem("device-group-spec"))

  override def afterAll(): Unit = shutdown(system)  // 在所有测试结束后, 关闭系统

  // 测试device group可以注册一个device actor
  "DeviceGroupActor" should "be able to register a device actor" in {
    val probe = TestProbe()
    val groupActor = system.actorOf(DeviceGroup.props("group"))  // 实例化一个device group actor

    groupActor.tell(DeviceManager.RequestTrackDevice("group", "device1"), probe.ref)  // 以probe为发送者, 向groupActor发送注册信息
    probe.expectMsg(DeviceManager.DeviceRegistered)
    val deviceActor1 = probe.lastSender  // group只是转发消息, 最后的消息发送者应该是device actor

    groupActor.tell(DeviceManager.RequestTrackDevice("group", "device2"), probe.ref)
    probe.expectMsg(DeviceManager.DeviceRegistered)
    val deviceActor2 = probe.lastSender
    deviceActor1 should not be (deviceActor2)  // 注册了两个device, 自然不同

    deviceActor1.tell(Device.RecordTemperature(requestId = 0, 1.0), probe.ref)  // 让device记录温度
    probe.expectMsg(Device.TemperatureRecorded(requestId = 0))
    deviceActor2.tell(Device.RecordTemperature(requestId = 1, 2.0), probe.ref)
    probe.expectMsg(Device.TemperatureRecorded(requestId = 1))
  }

  // 若groupId错误, 则groupActor忽略请求
  "DeviceGroupActor" should "ignore requests for wrong groupId" in {
    val probe = TestProbe()
    val groupActor = system.actorOf(DeviceGroup.props("group"))

    groupActor.tell(DeviceManager.RequestTrackDevice("wrongGroup", "device1"), probe.ref)
    probe.expectNoMsg(500.milliseconds)  // 即没有回复
  }

  // 注册同一device, 返回的应该是相同的device
  "DeviceGroupActor" should "return same actor for same deviceId" in {
    val probe = TestProbe()
    val groupActor = system.actorOf(DeviceGroup.props("group"))

    groupActor.tell(DeviceManager.RequestTrackDevice("group", "device1"), probe.ref)
    probe.expectMsg(DeviceManager.DeviceRegistered)
    val deviceActor1 = probe.lastSender

    groupActor.tell(DeviceManager.RequestTrackDevice("group", "device1"), probe.ref)
    probe.expectMsg(DeviceManager.DeviceRegistered)
    val deviceActor2 = probe.lastSender

    deviceActor1 should be (deviceActor2)
  }

  // 允许列出所有注册(激活)的device
  "DeviceGroupActor" should "be able to list active devices" in {
    val probe = TestProbe()
    val groupActor = system.actorOf(DeviceGroup.props("group"))

    groupActor.tell(DeviceManager.RequestTrackDevice("group", "device1"), probe.ref)
    probe.expectMsg(DeviceManager.DeviceRegistered)

    groupActor.tell(DeviceManager.RequestTrackDevice("group", "device2"), probe.ref)
    probe.expectMsg(DeviceManager.DeviceRegistered)

    groupActor.tell(DeviceGroup.RequestDeviceList(requestId = 0), probe.ref)
    probe.expectMsg(DeviceGroup.ReplyDeviceList(requestId = 0, Set("device1", "device2")))
  }

  // 在一个device停止后, 也能列出所有激活的device
  "DeviceGroupActor" should "be able to list active devices after one shuts down" in {
    val probe = TestProbe()
    val groupActor = system.actorOf(DeviceGroup.props("group"))

    groupActor.tell(DeviceManager.RequestTrackDevice("group", "device1"), probe.ref)
    probe.expectMsg(DeviceManager.DeviceRegistered)
    val toShutdown = probe.lastSender

    groupActor.tell(DeviceManager.RequestTrackDevice("group", "device2"), probe.ref)
    probe.expectMsg(DeviceManager.DeviceRegistered)

    groupActor.tell(DeviceGroup.RequestDeviceList(requestId = 0), probe.ref)
    probe.expectMsg(DeviceGroup.ReplyDeviceList(requestId = 0, Set("device1", "device2")))

    probe.watch(toShutdown)  // 用probe监视toShutdown
    toShutdown ! PoisonPill  // 向toShutdown发送PoisonPill消息, 当消息处理到PoisonPill后, actor停止
    probe.expectTerminated(toShutdown)  // 验证probe会收到Terminated消息

    // 每interval间隔会计算一次assert, 直到超时或不抛出异常
    probe.awaitAssert {  // 考虑收到Terminated消息可能较晚(毕竟是异步), 所以此处使用awaitAssert
      groupActor.tell(DeviceGroup.RequestDeviceList(requestId = 1), probe.ref)
      probe.expectMsg(DeviceGroup.ReplyDeviceList(requestId = 1, Set("device2")))
    }
  }

  // 对于工作正常的device能返回温度
  "DeviceGroupActor" should "return temperature value for working devices" in {
    val requester = TestProbe()

    val device1 = TestProbe()
    val device2 = TestProbe()

    val queryActor = system.actorOf(DeviceGroupQuery.props(  // device group查询, 即多查询
      actorToDeviceId = Map(device1.ref -> "device1", device2.ref -> "device2"),
      requestId = 1,
      requester = requester.ref,
      timeout = 3.seconds
    ))

    device1.expectMsg(Device.ReadTemperature(requestId = 0))  // 在queryActor实例化时, 就向deviceX发送了读温度的消息, 以ID为0
    device2.expectMsg(Device.ReadTemperature(requestId = 0))  // 所以此处期待此信息

    queryActor.tell(Device.RespondTemperature(requestId = 0, Some(1.0)), device1.ref)  // 因为请求的ID为0, 所以回复也为0
    queryActor.tell(Device.RespondTemperature(requestId = 0, Some(2.0)), device2.ref)

    requester.expectMsg(DeviceGroup.RespondAllTemperatures(
      requestId = 1,  // 这里要同queryActor的相同
      temperatures = Map(
        "device1" -> DeviceGroup.Temperature(1.0),
        "device2" -> DeviceGroup.Temperature(2.0)
      )
    ))
  }

  // 对于没有温度的device
  "DeviceGroupActor" should "return TemperatureNotAvailable for devices with no reading" in {
    val requester = TestProbe()

    val device1 = TestProbe()
    val device2 = TestProbe()

    val queryActor = system.actorOf(DeviceGroupQuery.props(
      actorToDeviceId = Map(device1.ref -> "device1", device2.ref -> "device2"),
      requestId = 1,
      requester = requester.ref,
      timeout = 3.seconds
    ))

    device1.expectMsg(Device.ReadTemperature(requestId = 0))
    device2.expectMsg(Device.ReadTemperature(requestId = 0))

    queryActor.tell(Device.RespondTemperature(requestId = 0, None), device1.ref)  // 回应, 但没有温度
    queryActor.tell(Device.RespondTemperature(requestId = 0, Some(2.0)), device2.ref)

    requester.expectMsg(DeviceGroup.RespondAllTemperatures(
      requestId = 1,
      temperatures = Map(
        "device1" -> DeviceGroup.TemperatureNotAvailable,
        "device2" -> DeviceGroup.Temperature(2.0)
      )
    ))
  }

  // device在回复消息前stop, 则返回DeviceNotAvaible
  "DeviceGroupActor" should "return DeviceNotAvaible if device stops before answering" in {
    val requester = TestProbe()

    val device1 = TestProbe()
    val device2 = TestProbe()

    val queryActor = system.actorOf(DeviceGroupQuery.props(
      actorToDeviceId = Map(device1.ref -> "device1", device2.ref -> "device2"),
      requestId = 1,
      requester = requester.ref,
      timeout = 3.seconds
    ))

    device1.expectMsg(Device.ReadTemperature(requestId = 0))
    device2.expectMsg(Device.ReadTemperature(requestId = 0))

    queryActor.tell(Device.RespondTemperature(requestId = 0, Some(1.0)), device1.ref)
    device2.ref ! PoisonPill  // 不发送消息, 终止

    requester.expectMsg(DeviceGroup.RespondAllTemperatures(
      requestId = 1,
      temperatures = Map(
        "device1" -> DeviceGroup.Temperature(1.0),
        "device2" -> DeviceGroup.DeviceNotAvailable  // 设备不可见
      )
    ))
  }

  // device在回复消息后才stop, 不影响返回温度
  "DeviceGroupActor" should "return temperature reading even if device stops after answering" in {
    val requester = TestProbe()

    val device1 = TestProbe()
    val device2 = TestProbe()

    val queryActor = system.actorOf(DeviceGroupQuery.props(
      actorToDeviceId = Map(device1.ref -> "device1", device2.ref -> "device2"),
      requestId = 1,
      requester = requester.ref,
      timeout = 3.seconds
    ))

    device1.expectMsg(Device.ReadTemperature(requestId = 0))
    device2.expectMsg(Device.ReadTemperature(requestId = 0))

    queryActor.tell(Device.RespondTemperature(requestId = 0, Some(1.0)), device1.ref)
    queryActor.tell(Device.RespondTemperature(requestId = 0, Some(2.0)), device2.ref)
    device2.ref ! PoisonPill  // 发送消息后终止

    requester.expectMsg(DeviceGroup.RespondAllTemperatures(
      requestId = 1,
      temperatures = Map(
        "device1" -> DeviceGroup.Temperature(1.0),
        "device2" -> DeviceGroup.Temperature(2.0)  // 无影响
      )
    ))
  }

  // 若device未及时回复消息, 则返回DeviceTimeout
  "DeviceGroupActor" should "return DeviceTimeout if device does not answer in time" in {
    val requester = TestProbe()

    val device1 = TestProbe()
    val device2 = TestProbe()

    val queryActor = system.actorOf(DeviceGroupQuery.props(
      actorToDeviceId = Map(device1.ref -> "device1", device2.ref -> "device2"),
      requestId = 1,
      requester = requester.ref,
      timeout = 1.seconds
    ))

    device1.expectMsg(Device.ReadTemperature(requestId = 0))
    device2.expectMsg(Device.ReadTemperature(requestId = 0))

    queryActor.tell(Device.RespondTemperature(requestId = 0, Some(1.0)), device1.ref)
    // device2不发送响应信息

    requester.expectMsg(DeviceGroup.RespondAllTemperatures(
      requestId = 1,
      temperatures = Map(
        "device1" -> DeviceGroup.Temperature(1.0),
        "device2" -> DeviceGroup.DeviceTimeout  // 收到超时
      )
    ))
  }

  // 综合测试
  "DeviceGroupSpec" should "be able to collect temperatures from all active devices" in {
    val probe = TestProbe()
    val groupActor = system.actorOf(DeviceGroup.props("group"))

    // 注册3个device
    groupActor.tell(DeviceManager.RequestTrackDevice("group", "device1"), probe.ref)
    probe.expectMsg(DeviceManager.DeviceRegistered)
    val deviceActor1 = probe.lastSender

    groupActor.tell(DeviceManager.RequestTrackDevice("group", "device2"), probe.ref)
    probe.expectMsg(DeviceManager.DeviceRegistered)
    val deviceActor2 = probe.lastSender

    groupActor.tell(DeviceManager.RequestTrackDevice("group", "device3"), probe.ref)
    probe.expectMsg(DeviceManager.DeviceRegistered)
    val deviceActor3 = probe.lastSender

    // 向两个device记录温度
    deviceActor1.tell(Device.RecordTemperature(requestId = 0, 1.0), probe.ref)
    probe.expectMsg(Device.TemperatureRecorded(requestId = 0))
    deviceActor2.tell(Device.RecordTemperature(requestId = 1, 2.0), probe.ref)
    probe.expectMsg(Device.TemperatureRecorded(requestId = 1))
    // device3没有温度

    groupActor.tell(DeviceGroup.RequestAllTemperatures(requestId = 0), probe.ref)
    probe.expectMsg(
      DeviceGroup.RespondAllTemperatures(
        requestId = 0,
        temperatures = Map(
          "device1" -> DeviceGroup.Temperature(1.0),
          "device2" -> DeviceGroup.Temperature(2.0),
          "device3" -> DeviceGroup.TemperatureNotAvailable
        )
      )
    )
  }
}
