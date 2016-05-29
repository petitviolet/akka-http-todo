package net.petitviolet.todoex.contract

import scala.concurrent.{ ExecutionContext, Future }

trait Presenter[C <: OutputCallbackPort[_]] {

  type UseCaseExecutor = C => Unit

  type Rendered

  def response(call: UseCaseExecutor)(implicit ec: ExecutionContext): Future[Rendered]
}
