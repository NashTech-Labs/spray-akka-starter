package com.knoldus.spoke

import akka.actor.Actor
import org.slf4j.LoggerFactory

trait HumanResources {
  def startShiftRoutine: Boolean
}

class HumanResourcesService extends Actor with HumanResources {
  val logger = LoggerFactory.getLogger(this.getClass().getName())

  def receive = {
    // Do some brain crunching calculation  
    case "BANG" =>
      startShiftRoutine; sender ! Some("Hello")
    // Default case
    case _ => logger.error("I did not understand the message that you sent!"); sender ! Some("Bad Message")
  }

  def startShiftRoutine: Boolean = { Thread.sleep(2000); true }

}