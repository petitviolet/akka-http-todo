package net.petitviolet.todoex.application

import akka.http.scaladsl.model.{ ContentTypes, HttpEntity }
import akka.http.scaladsl.testkit.ScalatestRouteTest
import net.petitviolet.todoex.adapter.repository.{ ToDoRepository, TestH2DBImpl }
import net.petitviolet.todoex.domain.todo.Todo
import org.scalatest.{ Matchers, WordSpec }

import scala.concurrent.Future

class RouteTest extends WordSpec with Matchers with ScalatestRouteTest with Routes with ToDoRepositoryTestImpl {

  "The ToDo service" should {

    "get todo detail by todo id" in {
      Get("/todo/1") ~> routes ~> check {
        responseAs[String] === """{}"""
      }
    }

    "get all todo detail " in {
      Get("/todo/all") ~> routes ~> check {
        responseAs[String] shouldEqual """[{"name":"TestB todo","id":1}]"""
      }
    }

    "save todo detail" in {
      Post("/todo/save", HttpEntity(ContentTypes.`application/json`, write(Todo("My test ToDo")))) ~> routes ~> check {
        responseAs[String] shouldEqual "ToDo has  been saved successfully"
      }
    }

    "update todo detail" in {
      Post("/todo/update", HttpEntity(ContentTypes.`application/json`, write(Todo("My test ToDo")))) ~> routes ~> check {
        responseAs[String] shouldEqual "ToDo has  been updated successfully"
      }
    }

    "delete todo detail by id" in {
      Post("/todo/delete/2") ~> routes ~> check {
        responseAs[String] shouldEqual "ToDo has been deleted successfully"
      }
    }

  }

}
// For testing
trait ToDoRepositoryTestImpl extends ToDoRepository with TestH2DBImpl {
  override def create(todo: Todo): Future[Int] = Future.successful(1)

  override def update(todo: Todo): Future[Int] = Future.successful(1)

  override def getById(id: Int): Future[Option[Todo]] = Future.successful(Some(Todo("TestB todo", Some(1))))

  override def getAll(): Future[List[Todo]] = Future.successful(List(Todo("TestB todo", Some(1))))

  override def delete(id: Int): Future[Int] = Future.successful(1)
}