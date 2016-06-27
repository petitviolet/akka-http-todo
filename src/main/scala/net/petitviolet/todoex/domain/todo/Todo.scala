package net.petitviolet.todoex.domain.todo

import net.petitviolet.todoex.contract.usecase.todo.TodoDTO

class Todo private (
  val id: Option[Int] = None,
  val name: String,
  val status: TodoStatus
)

object Todo {
  def apply(name: String): Todo = new Todo(None, name, NotCompleted)

  def convertFromDTO(todoDTO: TodoDTO) = new Todo(Option(todoDTO.id), todoDTO.name, TodoStatus(todoDTO.status))
}

sealed abstract class TodoStatus(val value: Int)
case object NotCompleted extends TodoStatus(0)
case object Done extends TodoStatus(1)

object TodoStatus {
  def apply(value: Int): TodoStatus = value match {
    case 1 => Done
    case _ => NotCompleted
  }

}

