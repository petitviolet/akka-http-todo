package net.petitviolet.todoex.contract.usecase.todo

import spray.json._

// standart DTO compatible with Todo object
case class TodoDTO(id: Int, name: String)

object TodoDTOJsonProtocol extends DefaultJsonProtocol {
  implicit val todoDTOProtocol: RootJsonFormat[TodoDTO] = jsonFormat2(TodoDTO.apply)
}

case class TodoNameDTO(name: String)

object TodoNameDTOJsonProtocol extends DefaultJsonProtocol {
  implicit val todoDTOProtocol: RootJsonFormat[TodoNameDTO] = jsonFormat1(TodoNameDTO.apply)
}

