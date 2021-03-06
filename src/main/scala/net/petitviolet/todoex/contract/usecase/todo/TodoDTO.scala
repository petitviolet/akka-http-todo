package net.petitviolet.todoex.contract.usecase.todo

import net.petitviolet.todoex.domain.todo.{ Todo, NotCompleted }
import spray.json._

// standart DTO compatible with Todo object
case class TodoDTO(id: Int, name: String, status: Int)

object TodoDTO {
  def apply(idDTO: TodoIdDTO, nameDTO: TodoNameDTO): TodoDTO =
    TodoDTO.apply(idDTO.id, nameDTO.name, nameDTO.status)
}

// represent Todo object with `None` id
case class TodoNameDTO(
  name: String,
  status: Int = 0 //NotCompleted.value
) // why knows `NotCompleted`...?

case class UpdateWithoutIdTodoDTO(
  name: Option[String],
  status: Option[Int]
)
case class UpdateTodoDTO(
  id: Int,
  name: Option[String],
  status: Option[Int]
)

// represent ID of Todo object
case class TodoIdDTO(id: Int)

object TodoDTOJsonProtocol extends DefaultJsonProtocol {
  implicit val todoDtoProtocol: RootJsonFormat[TodoDTO] = jsonFormat3(TodoDTO.apply)
  implicit val todoNameDtoProtocol: RootJsonFormat[TodoNameDTO] = jsonFormat2(TodoNameDTO.apply)
  implicit val todoIdDtoProtocol: RootJsonFormat[TodoIdDTO] = jsonFormat1(TodoIdDTO.apply)
  implicit val updateWithoutIdTodoDTOProtocol: RootJsonFormat[UpdateWithoutIdTodoDTO] = jsonFormat2(UpdateWithoutIdTodoDTO.apply)
  implicit val updateTodoDTOProtocol: RootJsonFormat[UpdateTodoDTO] = jsonFormat3(UpdateTodoDTO.apply)
}
