package net.petitviolet.todoex.application

import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server._
import akka.http.scaladsl.unmarshalling.Unmarshaller._
import net.petitviolet.todoex.adapter.presenter.todo._
import net.petitviolet.todoex.adapter.repository.{ MixInToDoRepository, UsesToDoRepository }
import net.petitviolet.todoex.contract.usecase.todo._
import net.petitviolet.todoex.domain.todo.Todo

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

object TodoController extends TodoController
  with MixInCreateTodoUseCase with MixInCreateTodoPresenter
  with MixInUpdateTodoUseCase with MixInUpdateTodoPresenter
  with MixInFindTodoUseCase with MixInFindTodoPresenter
  with MixInToDoRepository

trait TodoController extends JsonController
    with UsesCreateTodoUseCase with UsesCreateTodoPresenter
    with UsesUpdateTodoUseCase with UsesUpdateTodoPresenter
    with UsesFindTodoUseCase with UsesFindTodoPresenter
    with UsesToDoRepository {

  val route = pathPrefix("todo") {

    path("all") {
      get {
        complete {
          todoRepository.getAll().map { result => HttpResponse(entity = write(result)) }
        }
      }
    } ~
      ((path("search") & parameter('name).as[FindTodoDTO](FindByNameTodoDTO)) |
        path(IntNumber).as[FindTodoDTO](FindByIdTodoDTO)) { byId: FindTodoDTO =>
          get {
            import TodoDTOJsonProtocol._
            onSuccess(findTodoPresenter.response(
              findTodoUseCase.execute(byId)
            )) { dtoOpt => dtoOpt.map(dto => complete(dto)) getOrElse (throw NoContentException) }
          }
        } ~
        path("save") {
          import TodoNameDTOJsonProtocol._
          post {
            entity(as[TodoNameDTO]) { todo =>
              val savedTodoDTOFuture: Future[TodoDTO] = createTodoPresenter.response(
                createTodoUseCase.execute(todo)
              )

              import TodoDTOJsonProtocol._
              onSuccess(savedTodoDTOFuture) { todoDto =>
                complete(todoDto)
              }
            }
          }
        } ~
        path("update" / IntNumber) { id =>
          import TodoNameDTOJsonProtocol._
          put {
            entity(as[TodoNameDTO]) { todo =>
              val savedTodoDTOFuture: Future[TodoDTO] = createTodoPresenter.response(
                createTodoUseCase.execute(todo)
              )

              import TodoDTOJsonProtocol._
              onSuccess(savedTodoDTOFuture) { todoDto =>
                complete(todoDto)
              }
            }
          }
        } ~
        path("delete" / IntNumber) { id =>
          delete {
            complete {
              todoRepository.delete(id).map { result =>
                HttpResponse(entity = "ToDo has been deleted successfully")
              }
            }
          }
        }
  }

}

