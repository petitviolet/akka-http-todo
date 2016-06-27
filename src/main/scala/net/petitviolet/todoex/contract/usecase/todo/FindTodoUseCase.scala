package net.petitviolet.todoex.contract.usecase.todo

import net.petitviolet.todoex.adapter.repository.{ MixInToDoRepository, UsesToDoRepository }
import net.petitviolet.todoex.contract.{ InputPort, UseCase }
import net.petitviolet.todoex.domain.todo.{ TodoStatus, Todo }

import scala.concurrent.{ ExecutionContext, Future }

/**
 * UseCase for find Todo
 * receive input as a FindTodoDTO
 * then
 * find a Todo
 * and
 * response output as a Some(TodoDTO) if exists else None
 */
trait FindTodoUseCase extends UseCase
    with InputPort[FindTodoDTO, Seq[TodoDTO]] with UsesToDoRepository {

  override protected def call(arg: In)(implicit ec: ExecutionContext): Future[Out] = {
    (arg match {
      case FindAllTodoDTO =>
        todoRepository.getAll()
      case FindByIdTodoDTO(id) =>
        todoRepository.getById(id) map { _.toSeq }
      case FindByConditionTodoDTO(nameOpt, statusOpt) =>
        val res: Future[Seq[Todo]] = nameOpt.map { name =>
          todoRepository.getByName(name)
        } getOrElse todoRepository.getAll()

        statusOpt.fold(res) { statusInt: Int =>
          val status = TodoStatus(statusInt)
          res.map { todos => todos.filter { _.status == status } }
        }
    }).map { todoOpt =>
      for {
        todo: Todo <- todoOpt
        id <- todo.id
      } yield {
        TodoDTO(id, todo.name, todo.status.value)
      }
    }
  }

}

trait UsesFindTodoUseCase {
  val findTodoUseCase: FindTodoUseCase
}

trait MixInFindTodoUseCase {
  val findTodoUseCase: FindTodoUseCase = new FindTodoUseCaseImpl
}

class FindTodoUseCaseImpl extends FindTodoUseCase with MixInToDoRepository