package model

import anorm._
import anorm.SqlParser._
import play.api.Play.current
import play.api.db.DB

/**
 * @author ishizukyousuke on 2015/10/06.
 */
case class RSS(id: Long, name: String, url: String)

object RSS {
  DB.withConnection { implicit c =>
    val result: Boolean = SQL("Select 1").execute()
  }
  val simple = {
    get[Long]("id") ~
      get[String]("name") ~
      get[String]("url") map {
      case id ~ name ~ url => RSS(id, name, url)
    }
  }

  def findAll(): Seq[RSS] = {
    DB.withConnection { implicit connection =>
      SQL("SELECT * FROM RSS").as(RSS.simple *)
    }
  }

  def countAll: Long = {
    DB.withConnection { implicit connection =>
      SQL("SELECT count(*) FROM RSS").as(scalar[Long].single)
    }
  }
}