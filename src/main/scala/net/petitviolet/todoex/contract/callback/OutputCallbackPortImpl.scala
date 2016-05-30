package net.petitviolet.todoex.contract.callback

import net.petitviolet.todoex.contract.OutputCallbackPort

import scala.concurrent.Promise

class OutputCallbackPortImpl[A] extends OutputCallbackPort[A] {
  val promise = Promise[A]()
  override def onSuccess(result: A): Unit = promise.success(result)

  override def onFailure(t: Throwable): Unit = promise.failure(t)
}

