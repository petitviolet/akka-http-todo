package net.petitviolet.todoex.domain.todo

case class Todo(id: Option[Int] = None, name: String, status: TodoStatus = NotCompleted)

object Todo {
  //  def tupled(id: Option[Int], name: String, statusInt: Int): Todo =
  //    Todo(id, name, TodoStatus(statusInt))
}
//  createdAt: LocalDateTime = LocalDateTime.now(),
//  updatedAt: LocalDateTime = LocalDateTime.now())

sealed abstract class TodoStatus(val value: Int)
case object NotCompleted extends TodoStatus(0)
case object Done extends TodoStatus(1)

object TodoStatus {
  def apply(value: Int): TodoStatus = value match {
    case 1 => Done
    case _ => NotCompleted
  }

}

