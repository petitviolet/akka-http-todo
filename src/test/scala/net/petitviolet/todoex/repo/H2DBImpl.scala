package net.petitviolet.todoex.repo

import net.petitviolet.todoex.connection.DBComponent
import org.slf4j.LoggerFactory

trait TestH2DBImpl extends DBComponent {

  val logger = LoggerFactory.getLogger(this.getClass)

  val driver = slick.driver.H2Driver

  import driver.api._
  val h2Url = "jdbc:h2:mem:todo-db;MODE=MySql;DATABASE_TO_UPPER=false;INIT=runscript from 'src/test/resources/schema.sql'\\;runscript from 'src/test/resources/schemadata.sql'"
  //  val h2Url = "jdbc:h2:mem:todo-db;MODE=MySql;"

  val db: Database = {
    logger.info("Creating test connection ..................................")
    Database.forURL(url = h2Url, driver = "org.h2.Driver")
  }
}
