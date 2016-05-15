package net.petitviolet.todoex.service

import akka.http.scaladsl.model.HttpResponse
import akka.http.scaladsl.server.Directives._
import net.petitviolet.todoex.json.JsonHelper
import net.petitviolet.todoex.repo.{ ToDo, ToDoRepository }
import scala.concurrent.ExecutionContext.Implicits.global

trait Routes extends JsonHelper { this: ToDoRepository =>

  val routes = {

    path("todo" / IntNumber) { id =>
      get {
        complete {
          getById(id).map { result =>
            if (result.isDefined)
              HttpResponse(entity = write(result.get))
            else
              HttpResponse(entity = "This todo does not exist")
          }

        }
      }
    } ~
      path("todo" / "all") {
        get {
          complete {
            getAll().map { result => HttpResponse(entity = write(result)) }
          }
        }
      } ~
      path("todo" / "save") {
        post {
          entity(as[String]) { todoJson =>
            complete {
              val todo = parse(todoJson).extract[ToDo]
              create(todo).map { result => HttpResponse(entity = "ToDo has  been saved successfully") }
            }
          }
        }
      } ~
      path("todo" / "update") {
        post {
          entity(as[String]) { todoJson =>
            complete {
              val todo = parse(todoJson).extract[ToDo]
              update(todo).map { result => HttpResponse(entity = "ToDo has  been updated successfully") }
            }
          }
        }
      } ~
      path("todo" / "delete" / IntNumber) { id =>
        post {
          complete {
            delete(id).map { result => HttpResponse(entity = "ToDo has been deleted successfully") }

          }
        }
      }
  }

}

