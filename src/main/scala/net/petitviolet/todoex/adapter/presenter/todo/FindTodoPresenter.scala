package net.petitviolet.todoex.adapter.presenter.todo

import net.petitviolet.todoex.contract.callback.OutputCallbackPortImpl
import net.petitviolet.todoex.contract.usecase.todo.TodoDTO
import net.petitviolet.todoex.contract.{ OutputCallbackPort, Presenter }

import scala.concurrent.{ ExecutionContext, Future }

trait FindTodoPresenter extends Presenter[OutputCallbackPort[Seq[TodoDTO]]] {
  type Rendered = Seq[TodoDTO]

  override def response(call: UseCaseExecutor)(implicit ec: ExecutionContext): Future[Rendered] = {
    val callback = new OutputCallbackPortImpl[Rendered]
    call(callback)
    callback.promise.future
  }

}

trait UsesFindTodoPresenter {
  val findTodoPresenter: FindTodoPresenter
}

trait MixInFindTodoPresenter {
  val findTodoPresenter: FindTodoPresenter = new FindTodoPresenterImpl
}

class FindTodoPresenterImpl extends FindTodoPresenter