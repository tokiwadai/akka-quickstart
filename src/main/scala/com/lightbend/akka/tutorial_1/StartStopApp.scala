package com.lightbend.akka.tutorial_1

import akka.actor.{ Actor, ActorSystem, Props }
import scala.io.StdIn

class StartStopActor1 extends Actor {
  override def preStart(): Unit = {
    println("first started")
    context.actorOf(Props[StartStopActor2], "second")
  }
  override def postStop(): Unit = println("first stopped")

  override def receive: Receive = {
    case "stop" => context.stop(self)
  }
}

class StartStopActor2 extends Actor {
  override def preStart(): Unit = println("second started")
  override def postStop(): Unit = println("second stopped")

  // Actor.emptyBehavior is a useful placeholder when we don't
  // want to handle any messages in the actor.
  override def receive: Receive = Actor.emptyBehavior
}

object StartStopApp extends App {
  // Create the 'helloAkka' actor system
  val system: ActorSystem = ActorSystem("startStopActor")

  try {
    val first = system.actorOf(Props[StartStopActor1], "first")
    first ! "stop"

    println(">>> Press ENTER to exit <<<")
    StdIn.readLine()
  } finally {
    system.terminate()
  }
}