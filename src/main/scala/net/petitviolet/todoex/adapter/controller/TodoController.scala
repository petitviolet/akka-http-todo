package net.petitviolet.todoex.adapter.controller

import akka.http.scaladsl.model.ws.{ TextMessage, Message }
import akka.http.scaladsl.server.Directive
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.unmarshalling.Unmarshaller._
import akka.stream.scaladsl.Flow
import net.petitviolet.todoex.adapter.presenter.todo._
import net.petitviolet.todoex.adapter.repository.{ MixInToDoRepository, UsesToDoRepository }
import net.petitviolet.todoex.application.Context
import net.petitviolet.todoex.contract.usecase.todo.TodoDTOJsonProtocol._
import net.petitviolet.todoex.contract.usecase.todo._

import scala.concurrent.duration.Duration
import scala.concurrent.{ Await, Future }

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

  private val findTodoPath: Directive[Tuple1[FindTodoDTO]] =
    ((pathEndOrSingleSlash | path("all")) & provide(FindAllTodoDTO)) |
      (path("search") & parameters('name.?, 'status.as[Int].?).as[FindTodoDTO](FindByConditionTodoDTO.apply _)) |
      (path(IntNumber).as[FindTodoDTO](FindByIdTodoDTO))

  private def findTodoRoute = findTodoPath { findDto: FindTodoDTO =>
    get {
      val findTodoDTOFuture = findTodoPresenter.response(
        findTodoUseCase.execute(findDto)
      )
      completeFuture(findTodoDTOFuture)
    }
  }

  private def findWSTodoRoute = (path("ws") & get) {
    handleWebSocketMessages {
      Flow[Message].map {
        case TextMessage.Strict("all") => {
          val findTodoDTOFuture = findTodoPresenter.response(
            findTodoUseCase.execute(FindAllTodoDTO)
          )

          val res = Await.result(findTodoDTOFuture, Duration.Inf)
          TextMessage(res.mkString(","))
        }
        case _ => TextMessage("Message type unsupported")
      }
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
      (put & entity(as[UpdateWithoutIdTodoDTO])) { updateWithoutIdDto =>
        val updatedTodoDTOFuture: Future[UpdateTodoDTO] = updateTodoPresenter.response(
          updateTodoUseCase.execute(UpdateTodoDTO(idDto.id, updateWithoutIdDto.name, updateWithoutIdDto.status))
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
      findWSTodoRoute ~
      saveTodoRoute ~
      updateTodoRoute ~
      deleteTodoRoute
  }

}

