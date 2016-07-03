package net.petitviolet.todoex.adapter.repository

import net.petitviolet.todoex.adapter.infra.table.ToDoTable
import net.petitviolet.todoex.adapter.infra.{ MySQLDBImpl, DBComponent }
import net.petitviolet.todoex.contract.usecase.todo.UpdateTodoDTO
import net.petitviolet.todoex.domain.todo.Todo
import slick.dbio
import slick.dbio.Effect.Write
import slick.profile.FixedSqlAction

import scala.concurrent.Future

trait ToDoRepository extends ToDoTable {
  this: DBComponent =>

  import driver.api._

  /**
   * @param todo
   * create new todo
   */
  def create(todo: Todo): Future[Int] = db.run {
    todoTableAutoInc += todo
  }

  /**
   * @param todo
   * update existing todo
   */
  def update(todo: Todo): Future[Int] = db.run {
    todoTableQuery.filter(_.id === todo.id.get).update(todo)
  }

  def update(dto: UpdateTodoDTO): Future[Int] = {
    val all = todoTableQuery.filter(_.id === dto.id)
    val updateNameOpt = dto.name.map { name => all.map { _.name }.update(name) }
    val updateStatusOpt = dto.status.map { status => all.map { _.status }.update(status) }
    val query = Seq[Option[DBIOAction[Int, NoStream, Write]]](updateNameOpt, updateStatusOpt)
      .collect { case opt if opt.isDefined => opt.get }
      .reduceLeft((i, j) => i >> j)
    db.run { query }
  }

  /**
   * @param id
   * Get todo by id
   */
  def getById(id: Int): Future[Option[Todo]] = db.run {
    todoTableQuery.filter(_.id === id).result.headOption
  }

  def getByName(name: String): Future[Seq[Todo]] = db.run {
    todoTableQuery.filter(_.name like name).result
  }

  /**
   * @return
   * Get all todos
   */
  def getAll(): Future[List[Todo]] = db.run {
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

//use this for production
object ToDoRepositoryImpl extends MySQLDBImpl with ToDoRepository

trait UsesToDoRepository {
  val todoRepository: ToDoRepository
}

trait MixInToDoRepository {
  val todoRepository: ToDoRepository = ToDoRepositoryImpl
}

