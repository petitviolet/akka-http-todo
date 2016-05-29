package net.petitviolet.todoex.adapter.presenter.todo

import net.petitviolet.todoex.contract.callback.todo.TodoOutputCallbackPortImpl
import net.petitviolet.todoex.contract.usecase.todo.TodoDTO
import net.petitviolet.todoex.contract.{OutputCallbackPort, Presenter}

import scala.concurrent.{ExecutionContext, Future}

trait UpdateTodoPresenter extends Presenter[OutputCallbackPort[TodoDTO]] {
  type Rendered = TodoDTO

  override def response(call: UseCaseExecutor)(implicit ec: ExecutionContext): Future[Rendered] = {
    val callback = new TodoOutputCallbackPortImpl
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