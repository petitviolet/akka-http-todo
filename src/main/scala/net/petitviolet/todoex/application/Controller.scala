package net.petitviolet.todoex.application

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.server.Route
import net.petitviolet.todoex.support.JsonHelper

trait Controller {
  val route: Route
}

// sugar
trait JsonController extends JsonHelper with SprayJsonSupport
