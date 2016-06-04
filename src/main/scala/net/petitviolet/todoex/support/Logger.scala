package net.petitviolet.todoex.support

import akka.event.{ LoggingAdapter, Logging }
import net.petitviolet.todoex.application.UsesContext
import org.slf4j.LoggerFactory

trait Logger extends UsesContext {
  //  val logger: LoggingAdapter = Logging(context.system, "todo-ex")
  val logger = LoggerFactory.getLogger(getClass)
}
