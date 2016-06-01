package net.petitviolet.todoex.support

import akka.event.Logging
import net.petitviolet.todoex.application.UsesContext

trait Logger extends UsesContext {
  val logger = Logging(context.system, "todo-ex")
}
