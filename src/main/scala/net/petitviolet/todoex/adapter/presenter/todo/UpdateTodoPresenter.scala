package net.petitviolet.todoex.adapter.presenter.todo

import net.petitviolet.todoex.contract.callback.OutputCallbackPortImpl
import net.petitviolet.todoex.contract.usecase.todo.{ UpdateTodoDTO, TodoDTO }
import net.petitviolet.todoex.contract.{ OutputCallbackPort, Presenter }

import scala.concurrent.{ ExecutionContext, Future }

trait UpdateTodoPresenter extends Presenter[OutputCallbackPort[UpdateTodoDTO]] {
  type Rendered = UpdateTodoDTO

  override def response(call: UseCaseExecutor)(implicit ec: ExecutionContext): Future[Rendered] = {
    val callback = new OutputCallbackPortImpl[Rendered]
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