package net.petitviolet.todoex.contract.usecase.todo

import net.petitviolet.todoex.adapter.repository.{ MixInToDoRepository, UsesToDoRepository }
import net.petitviolet.todoex.contract.{ InputPort, UseCase }
import net.petitviolet.todoex.domain.todo.Todo

import scala.concurrent.{ ExecutionContext, Future }

trait UpdateTodoUseCase extends UseCase
    with InputPort[TodoDTO, TodoDTO] with UsesToDoRepository {

  override protected def call(arg: In)(implicit ec: ExecutionContext): Future[Out] = {
    val todo = dtoToEntity(arg)
    val idFuture = todoRepository.update(todo)

    idFuture map { id =>
      TodoDTO(id, arg.name)
    }
  }

  private def dtoToEntity(arg: In) =
    Todo(id = Some(arg.id), name = arg.name)
}

trait UsesUpdateTodoUseCase {
  val updateTodoUseCase: FindTodoUseCase
}

trait MixInUpdateTodoUseCase {
  val updateTodoUseCase: FindTodoUseCase = new FindTodoUseCaseImpl
}

class UpdateTodoUseCaseImpl extends FindTodoUseCase with MixInToDoRepository