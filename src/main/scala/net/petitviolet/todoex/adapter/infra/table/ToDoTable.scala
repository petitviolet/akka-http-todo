package net.petitviolet.todoex.adapter.infra.table

import net.petitviolet.todoex.adapter.infra.DBComponent
import net.petitviolet.todoex.domain.todo.Todo

trait ToDoTable {
  this: DBComponent =>

  import driver.api._

  class ToDoTable(tag: Tag) extends Table[Todo](tag, "todo") {
    val id = column[Int]("id", O.PrimaryKey, O.AutoInc)
    val name = column[String]("name")

    def * = (id.?, name) <> (Todo.tupled, Todo.unapply)

  }

  protected val todoTableQuery = TableQuery[ToDoTable]

  protected def todoTableAutoInc = todoTableQuery returning todoTableQuery.map(_.id)

}
