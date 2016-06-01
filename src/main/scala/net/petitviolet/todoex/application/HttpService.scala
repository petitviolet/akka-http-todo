package net.petitviolet.todoex.application

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import net.petitviolet.todoex.adapter.controller.TodoController
import net.petitviolet.todoex.adapter.repository.MixInToDoRepository

object HttpService extends App with MixInToDoRepository {

  implicit val system: ActorSystem = ActorSystem()

  implicit val materializer = ActorMaterializer()

  implicit val dispatcher = system.dispatcher

  //  toDoRepository.ddl.onComplete {
  //    _ =>
  //      toDoRepository.create(ToDo("SBI"))
  //      toDoRepository.create(ToDo("PNB"))
  //      toDoRepository.create(ToDo("RBS"))
  //  }

  val router = new Router(TodoController :: Nil)
  Http().bindAndHandle(router.routes, "localhost", 9000)

  /**
   *
   * Insert data when start
   *
   */

}
