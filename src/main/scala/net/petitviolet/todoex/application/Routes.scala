package net.petitviolet.todoex.application

import net.petitviolet.todoex.adapter.controller.TodoController

trait Routes {

  val routes = TodoController.route

}

