package cn.enali.example.iot

import akka.actor.{Actor, ActorLogging, Props}

class IotSupervisor extends Actor with ActorLogging {
  override def preStart(): Unit = log.info("IoT Application started")

  override def postStop(): Unit = log.info("IoT Application stopped")

  override def receive: Receive = Actor.emptyBehavior  // 什么也不做
}

object IotSupervisor {
  def props = Props[IotSupervisor]
}
