package net.petitviolet.todoex.adapter.presenter.todo

import net.petitviolet.todoex.contract.callback.todo.TodoCallbackImpl
import net.petitviolet.todoex.contract.usecase.todo.TodoDTO
import net.petitviolet.todoex.contract.{ Callback, Presenter }

import scala.concurrent.{ ExecutionContext, Future, Promise }

trait UpdateTodoPresenter extends Presenter[Callback[TodoDTO]] {
  type Rendered = TodoDTO

  override def response(call: UseCaseExecutor)(implicit ec: ExecutionContext): Future[Rendered] = {
    val callback = new TodoCallbackImpl
    call(callback)
    callback.promise.future
  }

}

trait UsesUpdateTodoPresenter {
  val updateTodoPresenter: UpdateTodoPresenter
}

trait MixInUpdateTodoPresenter {
  val updateTodoPresenter: UpdateTodoPresenter = new UpdateTodoPresenterImpl
}

class UpdateTodoPresenterImpl extends UpdateTodoPresenter