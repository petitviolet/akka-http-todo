package net.petitviolet.todoex.repo

import org.scalatest.FunSuite
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.time.{ Millis, Seconds, Span }

import scala.concurrent.Await
import scala.concurrent.duration.Duration

class ToDoRepositoryTest extends FunSuite with ToDoRepository with TestH2DBImpl with ScalaFutures {

  implicit val defaultPatience = PatienceConfig(timeout = Span(5, Seconds), interval = Span(500, Millis))

  import scala.concurrent.ExecutionContext.Implicits.global
  //  ddl

  test("Add new todo ") {
    val response = create(ToDo("ICICI todo"))
    whenReady(response) { todoId =>
      assert(todoId === 4)
    }
  }

  test("Update  SBI todo  ") {
    val response = update(ToDo("SBI ToDo", Some(1)))
    whenReady(response) { res =>
      assert(res === 1)
    }
  }

  test("Delete SBI todo  ") {
    val response = delete(2)
    whenReady(response) { res =>
      assert(res === 1)
    }
  }

  test("Get todo list") {
    val todoList = getAll()
    whenReady(todoList) { result =>
      assert(result === List(ToDo("SBI todo", Some(1)), ToDo("PNB todo", Some(2)), ToDo("RBS todo", Some(3))))
    }
  }

}