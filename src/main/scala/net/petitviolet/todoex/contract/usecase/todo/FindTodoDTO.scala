package net.petitviolet.todoex.contract.usecase.todo

import spray.json._

sealed trait FindTodoDTO
case class FindByIdTodoDTO(id: Int) extends FindTodoDTO
case class FindByNameTodoDTO(name: String) extends FindTodoDTO

object FindTodoDTOJsonProtocol extends DefaultJsonProtocol {
  implicit val findTodoDTOFormat: RootJsonReader[FindTodoDTO] =
    new RootJsonReader[FindTodoDTO] {
      override def read(json: JsValue): FindTodoDTO =
        json.asJsObject.getFields("id", "name") match {
          case Seq(JsNumber(id), _) => FindByIdTodoDTO(id.toInt)
          case Seq(_, JsString(name)) => FindByNameTodoDTO(name)
        }
    }
}

