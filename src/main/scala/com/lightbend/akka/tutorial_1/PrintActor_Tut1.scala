package com.lightbend.akka.tutorial_1

import akka.actor.{ Actor, ActorSystem, Props }
import scala.io.StdIn

class PrintMyActorRefActor extends Actor {
  override def receive: Receive = {
    case "printit" =>
      val secondRef = context.actorOf(Props.empty, "second-actor")
      println(s"Second: $secondRef")
  }
}

// main-class
object PrintActor_Tut1 extends App {
  import akka.actor.actorRef2Scala

  // Create the 'helloAkka' actor system
  val system: ActorSystem = ActorSystem("printMyActorRefActor")

  try {
    val firstRef = system.actorOf(Props[PrintMyActorRefActor], "first-actor")
    println(s"First : $firstRef")
    firstRef ! "printit"

    println(">>> Press ENTER to exit <<<")
    StdIn.readLine()
  } finally {
    system.terminate()
  }
}
