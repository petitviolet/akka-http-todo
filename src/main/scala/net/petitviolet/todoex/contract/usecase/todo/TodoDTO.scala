package net.petitviolet.todoex.contract.usecase.todo

import spray.json._

// standart DTO compatible with Todo object
case class TodoDTO(id: Int, name: String)

object TodoDTO {
  def apply(idDTO: TodoIdDTO, nameDTO: TodoNameDTO): TodoDTO =
    TodoDTO.apply(idDTO.id, nameDTO.name)
}

// represent Todo object with `None` id
case class TodoNameDTO(name: String)

// represent ID of Todo object
case class TodoIdDTO(id: Int)

object TodoDTOJsonProtocol extends DefaultJsonProtocol {
  implicit val todoDtoProtocol: RootJsonFormat[TodoDTO] =
    jsonFormat2(TodoDTO.apply: (Int, String) => TodoDTO)
  implicit val todoNameDtoProtocol: RootJsonFormat[TodoNameDTO] = jsonFormat1(TodoNameDTO.apply)
  implicit val todoIdDtoProtocol: RootJsonFormat[TodoIdDTO] = jsonFormat1(TodoIdDTO.apply)
}
