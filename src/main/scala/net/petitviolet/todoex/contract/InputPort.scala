package net.petitviolet.todoex.contract

import scala.concurrent.ExecutionContext
import scala.util.{ Failure, Success }

/**
 * InputPort is a boundary of layers Controller and UseCase
 * And InputPort should be implemented by UseCase object
 * @tparam Arg: Argument for UseCase#call
 * @tparam Result Return from UseCase#call wrapped by `Future`
 */
trait InputPort[Arg, Result] {
  self: UseCase =>

  override type In = Arg

  override type Out = Result

  /**
   * type of execute is curried function `Arg => Callback => Unit`
   *
   * @param arg
   * @param ec
   * @tparam T
   * @return
   */
  def execute[T <: OutputCallbackPort[Result]](arg: Arg)(implicit ec: ExecutionContext): (T) => Unit =
    (callback: T) => {
      call(arg).onComplete {
        case Success(result) =>
          callback.onSuccess(result)
        case Failure(t) =>
          callback.onFailure(t)
      }
    }
}
