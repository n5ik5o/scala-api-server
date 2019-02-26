package app.service

import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server._
import spray.json.DefaultJsonProtocol

case class User(id: Int, name: String)
case class CreateUserRequest(name: String)
case class UpdateUserRequest(name: String)

object UserJson extends DefaultJsonProtocol {
  implicit lazy val userFormat = jsonFormat2(User)
  implicit lazy val createUserRequestFormat = jsonFormat1(CreateUserRequest)
  implicit lazy val updateUserRequest = jsonFormat1(UpdateUserRequest)
}

class UserService {
  def createUser(name: String) = "createUser" + name

  def getUsers() = {
    "getUsers"
  }

  def getUser(id: Int) = "getUser" + id

  def updateUser(id: Int, name: String) = "updateUser" + id + name

  def deleteUser(id: Int) = "deleteUser" + id

  private val userNotFound = "user_not_found"
  // private def createUser(name: String)(implicit db: DB) =
  //   db.createUser(name) match {
  //     case Left(err) =>
  //       failWith(err)
  //     case Right(user) =>
  //       complete(user)
  //   }

  // private def getUsers()(implicit db: DB) =
  //   db.getUsers match {
  //     case Left(err) =>
  //       failWith(err)
  //     case Right(users) =>
  //       complete(users)
  //   }

  // private def getUser(id: Int)(implicit db: DB) =
  //   db.getUser(id) match {
  //     case Left(err) =>
  //       failWith(err)
  //     case Right(None) =>
  //       complete(StatusCodes.NotFound -> ErrorResponse(userNotFound))
  //     case Right(Some(user)) =>
  //       complete(user)
  //   }

  // private def updateUser(id: Int, name: String)(implicit db: DB) =
  //   db.updateUser(id, name) match {
  //     case Left(err) =>
  //       failWith(err)
  //     case Right(None) =>
  //       complete(StatusCodes.NotFound -> ErrorResponse(userNotFound))
  //     case Right(Some(user)) =>
  //       complete(user)
  //   }

  // private def deleteUser(id: Int)(implicit db: DB) =
  //   db.deleteUser(id) match {
  //     case Left(err) =>
  //       failWith(err)
  //     case Right(()) =>
  //       complete(StatusCodes.OK)
  //   }

}
