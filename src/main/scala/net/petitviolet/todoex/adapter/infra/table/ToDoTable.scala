package net.petitviolet.todoex.adapter.infra.table

import net.petitviolet.todoex.adapter.infra.DBComponent
import net.petitviolet.todoex.contract.usecase.todo.TodoDTO
import net.petitviolet.todoex.domain.todo.{ Todo, TodoStatus }

trait ToDoTable { this: DBComponent =>

  import driver.api._

  class ToDoTable(tag: Tag) extends Table[Todo](tag, "todo") {
    val id = column[Int]("id", O.PrimaryKey, O.AutoInc)
    val name = column[String]("name")
    val status = column[Int]("status")

    def * =
      (id.?, name, status).shaped <> (
        {
          case (_id, _name, _status) =>
            Todo.convertFromDTO(TodoDTO(_id.get, _name, _status))
        },
        {
          todo: Todo =>
            Some((todo.id, todo.name, todo.status.value))
        }

      )

  }

  protected val todoTableQuery = TableQuery[ToDoTable]

  protected def todoTableAutoInc = todoTableQuery returning todoTableQuery.map(_.id)

}
