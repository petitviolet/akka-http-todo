package net.petitviolet.todoex.service

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import net.petitviolet.todoex.repo.{ ToDo, ToDoRepositoryImpl }

object HttpService extends App with Routes with ToDoRepositoryImpl {

  implicit val system: ActorSystem = ActorSystem()

  implicit val materializer = ActorMaterializer()

  implicit val dispatcher = system.dispatcher
  ddl.onComplete {
    _ =>
      create(ToDo("SBI"))
      create(ToDo("PNB"))
      create(ToDo("RBS"))
      Http().bindAndHandle(routes, "localhost", 9000)
  }

  /**
   *
   * Insert data when start
   *
   */

}
