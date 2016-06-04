package net.petitviolet.todoex.application

import net.petitviolet.todoex.adapter.repository.MixInToDoRepository

import scala.io.StdIn

object HttpService extends App
    with MixInToDoRepository with MixInContext {
  import context._

  val server = new Server(routes, new ServerConfigImpl)
  server.start()
  StdIn.readLine()
  server.stop().foreach(_ => context.shutdown())

}
