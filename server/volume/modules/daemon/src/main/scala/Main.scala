package my.daemon.app

import org.apache.commons.daemon._
import org.slf4j.LoggerFactory

import app._

class ApplicationDaemon extends Daemon {

  def init(daemonContext: DaemonContext): Unit = {}

  val app: ApplicationLifecycle = new Application
  def start() = app.start()
  def stop() = app.stop()
  def destroy() = app.stop()
}

object Main extends App {
  val logger = LoggerFactory.getLogger("Main")
  val app = new ApplicationDaemon
  app.start()
  logger.info("Press RETURN to stop...")
  scala.io.StdIn.readLine()
  app.stop()
}
