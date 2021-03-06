package net.petitviolet.todoex.adapter.controller

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.marshalling.ToResponseMarshaller
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import net.petitviolet.todoex.application.Context
import net.petitviolet.todoex.support.JsonHelper

import scala.concurrent.{ ExecutionContext, Future }

trait Controller {
  implicit val context: Context
  protected implicit val ec: ExecutionContext = context.executor
  val route: Route
}

// sugar
trait JsonController extends Controller with JsonHelper with SprayJsonSupport {
  protected def completeFuture[A](resultFuture: Future[A])(implicit marshaller: ToResponseMarshaller[A]) =
    onSuccess(resultFuture) { result =>
      complete(result)
    }
}
