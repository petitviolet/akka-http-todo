package net.petitviolet.todoex.contract.usecase.todo

import spray.json._

// standart DTO compatible with Todo object
case class TodoDTO(id: Int, name: String)

object TodoDTOJsonProtocol extends DefaultJsonProtocol {
  implicit val todoDtoProtocol: RootJsonFormat[TodoDTO] = jsonFormat2(TodoDTO.apply)
}

// represent Todo object with `None` id
case class TodoNameDTO(name: String)

object TodoNameDTOJsonProtocol extends DefaultJsonProtocol {
  implicit val todoNameDtoProtocol: RootJsonFormat[TodoNameDTO] = jsonFormat1(TodoNameDTO.apply)
}

// represent ID of Todo object
case class TodoIdDTO(id: Int)

object TodoIdDTOJsonProtocol extends DefaultJsonProtocol {
  implicit val todoIdDtoProtocol: RootJsonFormat[TodoIdDTO] = jsonFormat1(TodoIdDTO.apply)
}
