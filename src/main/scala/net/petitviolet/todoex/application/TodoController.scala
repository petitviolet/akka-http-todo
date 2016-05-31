package net.petitviolet.todoex.application

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.unmarshalling.Unmarshaller._
import net.petitviolet.todoex.adapter.presenter.todo._
import net.petitviolet.todoex.adapter.repository.{ MixInToDoRepository, UsesToDoRepository }
import net.petitviolet.todoex.contract.usecase.todo.TodoDTOJsonProtocol._
import net.petitviolet.todoex.contract.usecase.todo._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

object TodoController extends TodoController
  with MixInCreateTodoUseCase with MixInCreateTodoPresenter
  with MixInUpdateTodoUseCase with MixInUpdateTodoPresenter
  with MixInFindTodoUseCase with MixInFindTodoPresenter
  with MixInDeleteTodoUseCase with MixInDeleteTodoPresenter
  with MixInToDoRepository

trait TodoController extends JsonController
    with UsesCreateTodoUseCase with UsesCreateTodoPresenter
    with UsesUpdateTodoUseCase with UsesUpdateTodoPresenter
    with UsesFindTodoUseCase with UsesFindTodoPresenter
    with UsesDeleteTodoUseCase with UsesDeleteTodoPresenter
    with UsesToDoRepository {

  val route = pathPrefix("todo") {

    (path("all") & provide(FindAllTodoDTO) |
      path("search") & parameter('name).as[FindTodoDTO](FindByNameTodoDTO) |
      path(IntNumber).as[FindTodoDTO](FindByIdTodoDTO)) { findDto: FindTodoDTO =>
        get {
          onSuccess(findTodoPresenter.response(
            findTodoUseCase.execute(findDto)
          )) { dtos => complete(dtos) }
        }
      } ~
      path("save") {
        post {
          entity(as[TodoNameDTO]) { todo =>
            val savedTodoDTOFuture: Future[TodoDTO] = createTodoPresenter.response(
              createTodoUseCase.execute(todo)
            )

            onSuccess(savedTodoDTOFuture) { todoDto =>
              complete(todoDto)
            }
          }
        }
      } ~
      path("update" / IntNumber) { id =>
        put {
          entity(as[TodoNameDTO]) { todo =>
            val savedTodoDTOFuture: Future[TodoDTO] = createTodoPresenter.response(
              createTodoUseCase.execute(todo)
            )

            onSuccess(savedTodoDTOFuture) { todoDto =>
              complete(todoDto)
            }
          }
        }
      } ~
      path("delete" / IntNumber).as(TodoIdDTO) { idDto =>
        delete {
          val deleteTodoDTOFuture = deleteTodoPresenter.response(
            deleteTodoUseCase.execute(idDto)
          )

          onSuccess(deleteTodoDTOFuture) { todoDto =>
            complete(todoDto)
          }
        }
      }
  }

}

