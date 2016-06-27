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

    idFuture map { _ => arg }
  }

  private def dtoToEntity(arg: In) = Todo.convertFromDTO(arg)
}

trait UsesUpdateTodoUseCase {
  val updateTodoUseCase: UpdateTodoUseCase
}

trait MixInUpdateTodoUseCase {
  val updateTodoUseCase: UpdateTodoUseCase = new UpdateTodoUseCaseImpl
}

class UpdateTodoUseCaseImpl extends UpdateTodoUseCase with MixInToDoRepository