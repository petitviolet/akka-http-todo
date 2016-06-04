package net.petitviolet.todoex

import akka.http.scaladsl.server.Route
import net.petitviolet.todoex.adapter.controller.TodoControllerImpl

package object application {
  def routes(implicit context: Context): Route = {
    val router = new Router(new TodoControllerImpl :: Nil)
    router.routes
  }
}
