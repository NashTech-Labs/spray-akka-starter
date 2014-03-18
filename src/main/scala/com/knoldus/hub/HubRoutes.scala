package com.knoldus.hub

import spray.routing.HttpService
import com.knoldus.util.ActorHelper
import org.slf4j.LoggerFactory
import spray.http._
import spray.http.MediaTypes._
import spray.httpx.unmarshalling.FormField
import spray.routing._
import spray.routing.HttpService
import akka.actor.Props
import akka.actor.ActorRef
import akka.pattern.ask
import com.knoldus.spoke.HumanResourcesService
import scala.concurrent.Future
import scala.util.Failure
import scala.util.Success
import scala.concurrent._
import scala.concurrent.ExecutionContext.Implicits.global

trait HubRoutes extends HttpService with ActorHelper {
  val logger = LoggerFactory.getLogger(this.getClass().getName())
  val hrService: ActorRef = hubActorSystem.actorOf(Props[HumanResourcesService])

  val hubRoutes = {
    path("module" / Segment) { (emailText) =>
      get {
        logger.info("Get called with request")
        respondWithMediaType(`text/html`) { ctx =>
          val future = hrService ? emailText
          processFutureResult(future, ctx)
        }
      }
    } ~
      path("module") {
        entity(as[String]) { emailData =>
          post {
            respondWithMediaType(`text/html`) { ctx =>
              ctx.complete(s"Currently I cannot process data with $emailData")
            }
          }
        }
      }
  }

  private def processFutureResult(future: Future[Any], ctx: RequestContext) = {
    future.mapTo[Option[String]] onComplete {
      case Success(data) => {
        ctx.complete(data.getOrElse("Server is not talking!"))
      }
      case Failure(data) => ctx.complete("Failure in getting results")
    }
  }

}