package net.petitviolet.todoex.contract

// Output Port
trait OutputCallbackPort[Result] {

  def onSuccess(result: Result): Unit

  def onFailure(t: Throwable): Unit
}
