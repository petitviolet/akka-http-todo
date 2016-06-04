package net.petitviolet.todoex.application

import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Route
import com.typesafe.config.ConfigFactory
import net.petitviolet.todoex.support.Logger

import scala.concurrent.Future

class Server(route: Route, serverConfig: ServerConfig)(implicit val context: Context) extends Logger {
  import context._
  import serverConfig._

  private var binding: Future[Http.ServerBinding] = _

  def start() = {
    logger.info(s"Starting service on $host:$port")
    binding = Http().bindAndHandle(
      Route.handlerFlow(route),
      host,
      port
    )
    binding
  }

  def stop(): Future[Unit] = {
    require(binding != null)
    logger.info(s"Stop server $host:$port")
    binding.flatMap(_.unbind())
  }

}

trait ServerConfig {
  val host: String
  val port: Int
}

class ServerConfigImpl extends ServerConfig {
  private val config = ConfigFactory.load()
  val (host, port) = (config.getString("http.interface"), config.getInt("http.port"))
}

