package net.petitviolet.todoex.application

import ch.qos.logback.classic.LoggerContext
import ch.qos.logback.core.util.StatusPrinter
import com.typesafe.config.ConfigFactory
import net.petitviolet.todoex.support.Logger
import org.apache.commons.daemon.{ Daemon, DaemonContext }
import org.slf4j.LoggerFactory

import scala.concurrent.Await
import scala.concurrent.duration.Duration

class ServerDaemon extends Daemon with Logger with MixInContext {
  var server: Server = _

  override def init(daemonContext: DaemonContext): Unit = {
    logger.info("daemon-init")
    val serverConfig = new ServerConfigImpl

    val lc = logger.asInstanceOf[LoggerContext]
    StatusPrinter.print(lc)
    server = new Server(routes, serverConfig)
  }

  override def start() = {
    logger.info("daemon-start")
    Await.ready(server.start(), Duration.Inf)
  }

  override def stop() = {
    logger.info("daemon-stop")
    Await.ready(server.stop(), Duration.Inf)
  }

  override def destroy(): Unit = {
    logger.info("daemon-destroy")
    context.shutdown()
  }
}

