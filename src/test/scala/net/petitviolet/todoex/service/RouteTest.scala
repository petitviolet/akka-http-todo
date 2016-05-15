package net.petitviolet.todoex.service

import akka.http.scaladsl.model.{ ContentTypes, HttpEntity }
import akka.http.scaladsl.testkit.ScalatestRouteTest
import net.petitviolet.todoex.repo.{ ToDoRepository, TestH2DBImpl, ToDo }
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
      Post("/todo/save", HttpEntity(ContentTypes.`application/json`, write(ToDo("My test ToDo")))) ~> routes ~> check {
        responseAs[String] shouldEqual "ToDo has  been saved successfully"
      }
    }

    "update todo detail" in {
      Post("/todo/update", HttpEntity(ContentTypes.`application/json`, write(ToDo("My test ToDo")))) ~> routes ~> check {
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
  override def create(todo: ToDo): Future[Int] = Future.successful(1)

  override def update(todo: ToDo): Future[Int] = Future.successful(1)

  override def getById(id: Int): Future[Option[ToDo]] = Future.successful(Some(ToDo("TestB todo", Some(1))))

  override def getAll(): Future[List[ToDo]] = Future.successful(List(ToDo("TestB todo", Some(1))))

  override def delete(id: Int): Future[Int] = Future.successful(1)
}