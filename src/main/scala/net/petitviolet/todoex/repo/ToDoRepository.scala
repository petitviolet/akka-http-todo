package net.petitviolet.todoex.repo

import net.petitviolet.todoex.connection.{ MySQLDBImpl, DBComponent }

import scala.concurrent.Future

trait ToDoRepository extends ToDoTable {
  this: DBComponent =>

  import driver.api._

  /**
   * @param todo
   * create new todo
   */
  def create(todo: ToDo): Future[Int] = db.run {
    todoTableAutoInc += todo
  }

  /**
   * @param todo
   * update existing todo
   */
  def update(todo: ToDo): Future[Int] = db.run {
    todoTableQuery.filter(_.id === todo.id.get).update(todo)
  }

  /**
   * @param id
   * Get todo by id
   */
  def getById(id: Int): Future[Option[ToDo]] = db.run {
    todoTableQuery.filter(_.id === id).result.headOption
  }

  /**
   * @return
   * Get all todos
   */
  def getAll(): Future[List[ToDo]] = db.run {
    todoTableQuery.to[List].result
  }

  /**
   * @param id
   * delete todo by id
   */
  def delete(id: Int): Future[Int] = db.run {
    todoTableQuery.filter(_.id === id).delete
  }

  def ddl = db.run {
    todoTableQuery.schema.create
  }

}

trait ToDoTable {
  this: DBComponent =>

  import driver.api._

  class ToDoTable(tag: Tag) extends Table[ToDo](tag, "todo") {
    val id = column[Int]("id", O.PrimaryKey, O.AutoInc)
    val name = column[String]("name")

    def * = (name, id.?) <> (ToDo.tupled, ToDo.unapply)

  }

  protected val todoTableQuery = TableQuery[ToDoTable]

  protected def todoTableAutoInc = todoTableQuery returning todoTableQuery.map(_.id)

}

//use this for production
trait ToDoRepositoryImpl extends MySQLDBImpl with ToDoRepository

case class ToDo(name: String, id: Option[Int] = None)
