package net.petitviolet.todoex.contract.callback.todo

import net.petitviolet.todoex.contract.Callback
import net.petitviolet.todoex.contract.usecase.todo.TodoDTO

import scala.concurrent.Promise

class TodoCallbackImpl extends Callback[TodoDTO] {
  val promise = Promise[TodoDTO]()
  override def onSuccess(result: TodoDTO): Unit = promise.success(result)

  override def onFailure(t: Throwable): Unit = promise.failure(t)
}

