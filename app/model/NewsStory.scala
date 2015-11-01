package model

import anorm.SqlParser._
import anorm._
import play.api.db.DB
import play.api.libs.json.Json
import play.api.Play.current


/**
 * @author ishizukyousuke on 2015/10/02.
 */
case class NewsStory(info_url: String, url: String, title: String, info_title: String, info_title_facet: String,
                     summary: String, intended_reader: String, updated: String) {
}

object NewsStory {
  implicit def jsonWrites = Json.writes[NewsStory]

  implicit def jsonReads = Json.reads[NewsStory]

  DB.withConnection { implicit c =>
    val result: Boolean = SQL("Select 1").execute()
  }
  val simple = {
    get[String]("info_url") ~
      get[String]("url") ~
      get[String]("title") ~
      get[String]("info_title") ~
      get[String]("info_title_facet") ~
      get[String]("summary") ~
      get[String]("intended_reader") ~
      get[String]("updated") map {
      case info_url ~ url ~ title ~ info_title ~ info_title_facet ~ summary ~ intended_reader ~ updated =>
        NewsStory(info_url, url, title, info_title, info_title_facet,
          summary, intended_reader, updated)
    }
  }

  def findAll(): Seq[NewsStory] = {
    DB.withConnection { implicit connection =>
      SQL("SELECT * FROM NewsStory").as(NewsStory.simple *)
    }
  }

  def countAll: Long = {
    DB.withConnection { implicit connection =>
      SQL("SELECT count(*) FROM RSS").as(scalar[Long].single)
    }
  }
}