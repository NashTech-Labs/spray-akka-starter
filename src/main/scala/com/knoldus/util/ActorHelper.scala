package com.knoldus.util

import scala.concurrent._
import scala.concurrent.duration.DurationInt
import ExecutionContext.Implicits.global
import akka.util.Timeout
import akka.actor.ActorSystem

trait ActorHelper {
  val hubActorSystem = ActorSystem("HubActorSystem")
  implicit val timeout = Timeout(5 seconds)
}
