package net.petitviolet.todoex.adapter.controller

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.marshalling.ToResponseMarshaller
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.Directives._
import net.petitviolet.todoex.support.JsonHelper

import scala.concurrent.Future

trait Controller {
  val route: Route
}

// sugar
trait JsonController extends Controller with JsonHelper with SprayJsonSupport {
  protected def completeFuture[A](resultFuture: Future[A])(implicit marshaller: ToResponseMarshaller[A]) =
    onSuccess(resultFuture) { result =>
      complete(result)
    }
}
