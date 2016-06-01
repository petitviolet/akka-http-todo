package net.petitviolet.todoex.application

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server._
import net.petitviolet.todoex.adapter.controller.Controller

import scala.concurrent.ExecutionContext

class Router(controllers: Seq[Controller])(implicit dispatcher: ExecutionContext) {

  private val rejectionHandler =
    RejectionHandler
      .newBuilder()
      .handleNotFound {
        complete((StatusCodes.NotFound, "requested path was invalid."))
      }
      .handle {
        case r: Rejection =>
          complete((StatusCodes.BadRequest, s"something wrong: $r"))
      }
      .result()

  private val exceptionHandler = ExceptionHandler {
    case t: Throwable => complete((StatusCodes.InternalServerError, s"Error is $t"))
  }

  val routes =
    (handleExceptions(exceptionHandler) &
      handleRejections(rejectionHandler)) {
        controllers.map(_.route).reduceLeft(_ ~ _)
      }

}

