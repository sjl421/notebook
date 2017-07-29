package com.lightbend.akka.sample

import org.scalatest.{ BeforeAndAfterAll, FlatSpecLike, Matchers }
import akka.actor.{ Actor, Props, ActorSystem }
import akka.testkit.{ ImplicitSender, TestKit, TestActorRef, TestProbe }
import scala.concurrent.duration._
import Greeter._
import Printer._

// 测试类
class AkkaQuickstartSpec(_system: ActorSystem)
  extends TestKit(_system)  // 继承akka的TestKit
  with Matchers  // 下面3个都是混入了scalatest的测试组件
  with FlatSpecLike
  with BeforeAndAfterAll {

  def this() = this(ActorSystem("AkkaQuickstartSpec"))  // 定义了辅助构造函数, 若无参构造, 则新建ActorSystem

  override def afterAll: Unit = {  // 在测试结束后执行系统关闭
    shutdown(system)  // TODO system在哪定义的?
  }

  // 测试风格, "xxx" should "xxx" in { ... }
  "A Greeter Actor" should "pass on a greeting message when instructed to" in {
    val testProbe = TestProbe()
    val helloGreetingMessage = "hello"
    val helloGreeter = system.actorOf(Greeter.props(helloGreetingMessage, testProbe.ref))  //用testProbe.ref替代printerActor
    val greetPerson = "Akka"
    helloGreeter ! WhoToGreet(greetPerson)
    helloGreeter ! Greet
    testProbe.expectMsg(500 millis, Greeting(s"$helloGreetingMessage, $greetPerson"))  // 期待消息
  }
}
