package net.petitviolet.todoex.adapter.presenter.todo

import net.petitviolet.todoex.contract.callback.OutputCallbackPortImpl
import net.petitviolet.todoex.contract.usecase.todo.{ TodoIdDTO, TodoDTO }
import net.petitviolet.todoex.contract.{ OutputCallbackPort, Presenter }

import scala.concurrent.{ ExecutionContext, Future }

trait DeleteTodoPresenter extends Presenter[OutputCallbackPort[TodoIdDTO]] {
  type Rendered = TodoIdDTO

  override def response(call: UseCaseExecutor)(implicit ec: ExecutionContext): Future[Rendered] = {
    val callback = new OutputCallbackPortImpl[Rendered]
    call(callback)
    callback.promise.future
  }

}

trait UsesDeleteTodoPresenter {
  val deleteTodoPresenter: DeleteTodoPresenter
}

trait MixInDeleteTodoPresenter {
  val deleteTodoPresenter: DeleteTodoPresenter = new DeleteTodoPresenterImpl
}

class DeleteTodoPresenterImpl extends DeleteTodoPresenter