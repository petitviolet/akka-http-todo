package net.petitviolet.todoex.adapter.infra

import slick.driver.MySQLDriver

trait MySQLDBImpl extends DBComponent {

  val driver = MySQLDriver

  import driver.api._

  val db: Database = MySqlDB.connectionPool

}

private[infra] object MySqlDB {

  import slick.driver.MySQLDriver.api._

  val connectionPool = Database.forConfig("mysql")

}
