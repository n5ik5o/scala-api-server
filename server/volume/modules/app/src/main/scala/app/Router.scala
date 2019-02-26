package app

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route

import scala.concurrent.{ExecutionContextExecutor, Future}

class Router()(implicit ec: ExecutionContextExecutor) extends SprayJsonSupport {
  import app.service._
  import UserJson._

  val sys: SystemService = new SystemService
  val user: UserService = new UserService

  println(ec)

  val routes: Route =
    (get & pathSingleSlash) {
      // GET localhost:8080
      complete(sys.index())
    } ~ (get & path("ping")) {
      // GET localhost:8080/ping
      complete("pong")
    } ~ (get & path("http" / IntNumber)) { id =>
      Logic.validateCustomHeader { _ => request =>
        println(request)
        Logic.execute(id, request)
      }
    } ~ pathPrefix("users") {
      pathEndOrSingleSlash {
        get {
          // GET localhost:8080/users
          complete(user.getUsers())
        } ~ post {
          // POST localhost:8080/users
          entity(as[CreateUserRequest]) { request =>
            complete(user.createUser(request.name))
          }
        }
      } ~ path(IntNumber) { id =>
        get {
          // GET localhost:8080/users/:id
          complete(user.getUser(id))
        } ~ patch {
          // PATCH localhost:8080/users/:id
          entity(as[UpdateUserRequest]) { request =>
            complete(user.updateUser(id, request.name))
          }
        } ~ delete {
          // DELETE localhost:8080/users/:id
          complete(user.deleteUser(id))
        }
      }
    }
}

import akka.http.scaladsl.model._
import akka.http.scaladsl.model.headers._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.RouteResult.Complete
import akka.http.scaladsl.server._
import akka.http.scaladsl.model.headers.`Content-Type`

object Logic {

  val validateCustomHeader: Directive1[String] = {
    val value = headerValueByName("customHeaderKey")
    val res = value.filter(
      _ == "customHeaderValue",
      MalformedHeaderRejection("customHeaderKey", "value was wrong"))
    println(res.toString)
    res
  }

  def execute(num: Int, req: RequestContext)(
      implicit ec: ExecutionContextExecutor): Future[RouteResult] = {
    Future {
      println(req.request.headers.mkString(","))
      val header1 = `Set-Cookie`(HttpCookie("key", "value"))
      val response = HttpResponse(StatusCodes.OK,
                                  List(header1),
                                  HttpEntity(num.toString),
                                  HttpProtocols.`HTTP/1.1`)
      Complete(response)
    }
  }
}
