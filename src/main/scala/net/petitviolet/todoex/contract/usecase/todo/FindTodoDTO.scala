package net.petitviolet.todoex.contract.usecase.todo

import net.petitviolet.todoex.domain.todo.{ NotCompleted, TodoStatus }
import spray.json._

sealed trait FindTodoDTO
case object FindAllTodoDTO extends FindTodoDTO
final case class FindByIdTodoDTO(id: Int) extends FindTodoDTO
final case class FindByConditionTodoDTO(
  name: Option[String],
  status: Option[Int] = None
) extends FindTodoDTO

object FindTodoDTOJsonProtocol extends DefaultJsonProtocol {
  implicit val findTodoDTOFormat: RootJsonReader[FindTodoDTO] =
    new RootJsonReader[FindTodoDTO] {
      override def read(json: JsValue): FindTodoDTO =
        json.asJsObject.getFields("id", "name") match {
          case Seq(JsNumber(id), _) => FindByIdTodoDTO(id.toInt)
          case Seq(_, JsString(name)) => FindByConditionTodoDTO(Some(name))
        }
    }
}

