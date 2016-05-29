package net.petitviolet.todoex.contract.callback.todo

import net.petitviolet.todoex.contract.OutputCallbackPort
import net.petitviolet.todoex.contract.usecase.todo.TodoDTO

import scala.concurrent.Promise

class TodoOutputCallbackPortImpl extends OutputCallbackPort[TodoDTO] {
  val promise = Promise[TodoDTO]()
  override def onSuccess(result: TodoDTO): Unit = promise.success(result)

  override def onFailure(t: Throwable): Unit = promise.failure(t)
}

