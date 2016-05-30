package net.petitviolet.todoex.adapter.presenter.todo

import net.petitviolet.todoex.contract.callback.OutputCallbackPortImpl
import net.petitviolet.todoex.contract.usecase.todo.TodoDTO
import net.petitviolet.todoex.contract.{ OutputCallbackPort, Presenter }

import scala.concurrent.{ ExecutionContext, Future }

trait CreateTodoPresenter extends Presenter[OutputCallbackPort[TodoDTO]] {
  type Rendered = TodoDTO

  override def response(call: UseCaseExecutor)(implicit ec: ExecutionContext): Future[Rendered] = {
    val callback = new OutputCallbackPortImpl[TodoDTO]
    call(callback)
    callback.promise.future
  }

}

trait UsesCreateTodoPresenter {
  val createTodoPresenter: CreateTodoPresenter
}

trait MixInCreateTodoPresenter {
  val createTodoPresenter: CreateTodoPresenter = new CreateTodoPresenterImpl
}

class CreateTodoPresenterImpl extends CreateTodoPresenter