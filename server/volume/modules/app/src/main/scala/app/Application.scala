package app

import scala.concurrent.{Future}

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.Http
import org.slf4j.LoggerFactory
import com.typesafe.config.{Config, ConfigFactory}

trait ApplicationLifecycle {
  def start(): Unit
  def stop(): Unit
}

class Application extends ApplicationLifecycle {

  val logger = LoggerFactory.getLogger("Application")
  val applicationName = "myapp"

  implicit val system = ActorSystem(s"${applicationName}-system")
  implicit val materializer = ActorMaterializer()
  // implicit val ec = system.dispatcher //defaultDispatcher
  implicit val ec = system.dispatchers.lookup("blocking-io-dispatcher") //blockingDispatcher
  //implicit val ec: ExecutionContextExecutor = system.dispatchers.lookup("non-blocking-io-dispatcher") //nonBlockingDispatcher

  val config: Config = ConfigFactory.load()
  val host = config.getString("http.host")
  val port = config.getInt("http.port")

  val routes = new Router().routes
  var started: Boolean = false
  var bindingFuture: Future[Http.ServerBinding] = null

  def start(): Unit = {
    logger.info(s"Starting ${applicationName} Service")
    if (!started) {
      bindingFuture = Http().bindAndHandle(routes, host, port)
      logger.info(s"Server online at http://${host}:${port}/")
      started = true
      bindingFuture.onFailure {
        case ex: Exception => logger.error(ex + "Failed to bind!")
      }
    }
  }

  def stop(): Unit = {
    logger.info(s"Stopping ${applicationName} Service")
    if (started) {
      bindingFuture.flatMap(_.unbind()).onComplete(_ => system.terminate())
      started = false
    }
  }
}
