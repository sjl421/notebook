package cn.enali.example.iot

import akka.actor.ActorSystem

import scala.io.StdIn

/**
  * Created by enali on 2017/6/23.
  */

object IotApp {
  def main(args: Array[String]): Unit = {
    val system = ActorSystem("iot-system")
    try {
      val supervisor = system.actorOf(IotSupervisor.props, "iot-supervisor")

      StdIn.readLine()  // TODO 为什么读可以终止系统
    } finally {
      system.terminate()
    }
  }
}
