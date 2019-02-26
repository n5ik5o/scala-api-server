package app.service

import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._

class SystemService {

  def index() = HttpResponse(
    entity = HttpEntity(
      ContentTypes.`text/html(UTF-8)`,
      <html>
        <body>
          <h1>API Available!</h1>
        </body>
      </html>.toString
    )
  )

}
