package com.lightbend.akka.sample

import akka.actor.{ Actor, ActorLogging, ActorRef, ActorSystem, Props }
import scala.io.StdIn

// Greeter Actor的伴随对象, 用于定义Actor需要的消息类型, 以及props方法(描述Actor如何构建)
// 因为case class和case object能用于模式匹配, 因此常将消息类型定义为此
object Greeter {
  // Props是配置类, 用于描述Actor如何构建, 有类参数的常用Props(new A(args)); 没有类参数的常用Props[A]
  def props(message: String, printerActor: ActorRef): Props = Props(new Greeter(message, printerActor))
  final case class WhoToGreet(who: String)
  case object Greet
}

// Greeter Actor, 继承Actor
class Greeter(message: String, printerActor: ActorRef) extends Actor {
  import Greeter._  // 导入伴随对象内空, 主要有props和消息类型
  import Printer._

  var greeting = ""  // 内部状态

  // 所有Actor要定义receive方法, 偏函数, 只有case语句匹配
  def receive = {
    case WhoToGreet(who) =>
      greeting = s"$message, $who"  // 消息, 改变内部状态
    case Greet           =>
      printerActor ! Greeting(greeting)  // 消息, 向另一个Actor发送消息
  }
}

// Printer Actor的伴随对象, 定义了Greeting消息类
object Printer {
  def props: Props = Props[Printer]
  final case class Greeting(greeting: String)
}

// Printer Actor, 因继承了ActorLogging, 所以可在内部使用log方法
class Printer extends Actor with ActorLogging {
  import Printer._

  def receive = {
    case Greeting(greeting) =>
      log.info(s"Greeting received (from ${sender()}): $greeting")  // 只有一个Greeting消息, 打印
  }
}

object AkkaQuickstart extends App {
  import Greeter._

  val system: ActorSystem = ActorSystem("helloAkka")  // 创建ActorSystem, 参数为系统名称

  try {
    // 创建Actor, 这里用工厂函数返回的只是引用ActorRef, 参数为Props和Actor的名称(可省略)
    val printer: ActorRef = system.actorOf(Printer.props, "printerActor")

    val howdyGreeter: ActorRef =
      system.actorOf(Greeter.props("Howdy", printer), "howdyGreeter")
    val helloGreeter: ActorRef =
      system.actorOf(Greeter.props("Hello", printer), "helloGreeter")
    val goodDayGreeter: ActorRef =
      system.actorOf(Greeter.props("Good day", printer), "goodDayGreeter")

    howdyGreeter ! WhoToGreet("Akka")  // 向howdyGreeter Actor发送WhoToGreet的消息
    howdyGreeter ! Greet

    howdyGreeter ! WhoToGreet("Lightbend")
    howdyGreeter ! Greet

    helloGreeter ! WhoToGreet("Scala")
    helloGreeter ! Greet

    goodDayGreeter ! WhoToGreet("Play")
    goodDayGreeter ! Greet

    println(">>> Press ENTER to exit <<<")
    StdIn.readLine()
  } finally {
    system.terminate()  // 关闭系统
  }
}
