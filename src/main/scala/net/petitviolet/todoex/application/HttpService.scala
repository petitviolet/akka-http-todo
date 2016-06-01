package net.petitviolet.todoex.application

import net.petitviolet.todoex.adapter.controller.TodoControllerImpl
import net.petitviolet.todoex.adapter.repository.MixInToDoRepository

import scala.io.StdIn

object HttpService extends App
    with MixInToDoRepository with MixInContext {

  val router = new Router(new TodoControllerImpl :: Nil)
  val server = new Server(router.routes, new ServerConfigImpl)
  server.start()
  StdIn.readLine()
  server.stop()

}
