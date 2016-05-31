package net.petitviolet.todoex.contract.usecase.todo

import net.petitviolet.todoex.adapter.repository.{ MixInToDoRepository, UsesToDoRepository }
import net.petitviolet.todoex.contract.{ InputPort, UseCase }
import net.petitviolet.todoex.domain.todo.Todo

import scala.concurrent.{ ExecutionContext, Future }

trait DeleteTodoUseCase extends UseCase
    with InputPort[TodoIdDTO, TodoIdDTO] with UsesToDoRepository {

  override protected def call(arg: In)(implicit ec: ExecutionContext): Future[Out] = {
    val idFuture = todoRepository.delete(arg.id)

    idFuture map { _ =>
      arg
    }
  }
}

trait UsesDeleteTodoUseCase {
  val deleteTodoUseCase: DeleteTodoUseCase
}

trait MixInDeleteTodoUseCase {
  val deleteTodoUseCase: DeleteTodoUseCase = new DeleteTodoUseCaseImpl
}

class DeleteTodoUseCaseImpl extends DeleteTodoUseCase with MixInToDoRepository