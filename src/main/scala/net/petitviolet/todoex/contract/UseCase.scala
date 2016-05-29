package net.petitviolet.todoex.contract

import scala.concurrent.{ ExecutionContext, Future }

/**
  * input to output
  * `call` method execute something with `In`, and returns `Out` wrapped with `Future`
  * UseCase should implements InputPort
  */
trait UseCase {
  type In

  type Out

  protected def call(arg: In)(implicit ec: ExecutionContext): Future[Out]
}
