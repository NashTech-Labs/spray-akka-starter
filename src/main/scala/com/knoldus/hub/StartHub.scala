package com.knoldus.hub

import com.knoldus.util.ActorHelper
import akka.actor.Props
import akka.io.IO
import spray.can.Http
import akka.actor.ActorContext
import akka.actor.Actor
import spray.http.HttpRequest
import spray.http.HttpResponse
import spray.http.HttpResponse
import spray.routing.directives.LogEntry
import spray.http.HttpRequest
import akka.event.Logging.InfoLevel
import spray.routing.directives.LogEntry

object StartHub extends App with ActorHelper {

  implicit val system = hubActorSystem
  // create and start Email services
  val service = system.actorOf(Props[HubServices], "hubServices")
  // start a new HTTP server on port 8080 with our service actor as the handler
  IO(Http) ! Http.Bind(service, interface = "localhost", port = 8080)
}

class HubServices extends Actor with HubRoutes {
  def actorRefFactory: ActorContext = context

  // logs just the request method and response status at info level
  def requestMethodAndResponseStatusAsInfo(req: HttpRequest)(implicit requestTimestamp: Long = System.currentTimeMillis()): Any => Option[LogEntry] = {
    case res: HttpResponse => {
      val responseTimestamp: Long = System.currentTimeMillis()
      val elapsedTime: Long = responseTimestamp - requestTimestamp
      Some(LogEntry(req.method + ":" + req.uri + ":" + res.message.status + ":" + elapsedTime, akka.event.Logging.InfoLevel))
    }
    case _ => None // other kind of responses
  }


  def routeWithLogging = logRequestResponse(requestMethodAndResponseStatusAsInfo _)(hubRoutes)

  def receive: Receive = runRoute(routeWithLogging)
}