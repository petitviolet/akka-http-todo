package net.petitviolet.todoex.contract.usecase.todo

import net.petitviolet.todoex.adapter.repository.{ MixInToDoRepository, UsesToDoRepository }
import net.petitviolet.todoex.contract.{ InputPort, UseCase }
import net.petitviolet.todoex.domain.todo.Todo

import scala.concurrent.{ ExecutionContext, Future }

trait CreateTodoUseCase extends UseCase
    with InputPort[TodoNameDTO, TodoDTO] with UsesToDoRepository {

  override protected def call(arg: In)(implicit ec: ExecutionContext): Future[Out] = {
    val todo = dtoToEntity(arg)
    val idFuture = todoRepository.create(todo)

    idFuture map { id =>
      TodoDTO(id, todo.name, todo.status.value)
    }
  }

  private def dtoToEntity(arg: In) =
    Todo(name = arg.name)
}

trait UsesCreateTodoUseCase {
  val createTodoUseCase: CreateTodoUseCase
}

trait MixInCreateTodoUseCase {
  val createTodoUseCase: CreateTodoUseCase = new CreateTodoUseCaseImpl
}

class CreateTodoUseCaseImpl extends CreateTodoUseCase with MixInToDoRepository