package net.petitviolet.todoex.contract

import scala.concurrent.ExecutionContext
import scala.util.{ Failure, Success }

// Input Port
trait InputPullPort[PullArg, Arg, Result] extends InputPort[Arg, Result] {
  self: UseCase =>

  override type In = Arg

  override type Out = Result

  // Pull型は何かしらのパラメータを渡して、Inputを貰うという形
  def execute[T <: OutputCallbackPort[Result]](arg: PullArg => Arg)(callback: T)(implicit ec: ExecutionContext): Unit = {
    call(arg(pullArg)).onComplete {
      case Success(result) =>
        callback.onSuccess(result)
      case Failure(t) =>
        callback.onFailure(t)
    }
  }

  protected def pullArg: PullArg
}
