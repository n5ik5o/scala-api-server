import app._

object Main extends App {

  val app = new Application
  app.start()
  println("Press RETURN to stop...")
  scala.io.StdIn.readLine()
  app.stop()
}
