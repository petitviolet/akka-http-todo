package net.petitviolet.todoex.adapter.controller

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.unmarshalling.Unmarshaller._
import net.petitviolet.todoex.adapter.presenter.todo._
import net.petitviolet.todoex.adapter.repository.{ MixInToDoRepository, UsesToDoRepository }
import net.petitviolet.todoex.application.Context
import net.petitviolet.todoex.contract.usecase.todo.TodoDTOJsonProtocol._
import net.petitviolet.todoex.contract.usecase.todo._

import scala.concurrent.Future

class TodoControllerImpl(implicit val context: Context)
  extends TodoController with MixInToDoRepository
  with MixInCreateTodoUseCase with MixInCreateTodoPresenter
  with MixInUpdateTodoUseCase with MixInUpdateTodoPresenter
  with MixInFindTodoUseCase with MixInFindTodoPresenter
  with MixInDeleteTodoUseCase with MixInDeleteTodoPresenter

trait TodoController extends JsonController
    with UsesCreateTodoUseCase with UsesCreateTodoPresenter
    with UsesUpdateTodoUseCase with UsesUpdateTodoPresenter
    with UsesFindTodoUseCase with UsesFindTodoPresenter
    with UsesDeleteTodoUseCase with UsesDeleteTodoPresenter
    with UsesToDoRepository {

  private def findTodoRoute =
    (
      (pathEndOrSingleSlash | path("all")) & provide(FindAllTodoDTO) |
      path("search") & parameters('name.?, 'status.as[Int].?).as[FindTodoDTO](FindByConditionTodoDTO.apply _) |
      path(IntNumber).as[FindTodoDTO](FindByIdTodoDTO)
    ) { findDto: FindTodoDTO =>
        get {
          val findTodoDTOFuture = findTodoPresenter.response(
            findTodoUseCase.execute(findDto)
          )
          completeFuture(findTodoDTOFuture)
        }
      }

  private def saveTodoRoute =
    (path("save") & post & entity(as[TodoNameDTO])) { nameDto =>
      val savedTodoDTOFuture: Future[TodoDTO] = createTodoPresenter.response(
        createTodoUseCase.execute(nameDto)
      )

      completeFuture(savedTodoDTOFuture)
    }

  private def updateTodoRoute =
    path("update" / IntNumber).as(TodoIdDTO) { idDto =>
      (put & entity(as[TodoNameDTO])) { nameDto =>
        val updatedTodoDTOFuture: Future[TodoDTO] = updateTodoPresenter.response(
          updateTodoUseCase.execute(TodoDTO(idDto, nameDto))
        )

        completeFuture(updatedTodoDTOFuture)
      }
    }

  private def deleteTodoRoute =
    (path("delete" / IntNumber).as(TodoIdDTO) & delete) { idDto =>
      val deleteTodoDTOFuture = deleteTodoPresenter.response(
        deleteTodoUseCase.execute(idDto)
      )

      completeFuture(deleteTodoDTOFuture)
    }

  val route = pathPrefix("todo") {
    findTodoRoute ~
      saveTodoRoute ~
      updateTodoRoute ~
      deleteTodoRoute
  }

}

