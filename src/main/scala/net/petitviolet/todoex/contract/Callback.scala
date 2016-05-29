package net.petitviolet.todoex.contract

// Output Port
trait Callback[Result] {

  def onSuccess(result: Result): Unit

  def onFailure(t: Throwable): Unit
}
