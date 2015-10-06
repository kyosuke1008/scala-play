package model

import play.api.libs.json.Json

/**
 * @author ishizukyousuke on 2015/10/02.
 */
case class NewsStory(info_url: String, url: String, title: String, info_title: String, info_title_facet: String,
                     summary: String, intended_reader: String, updated: String) {
}

object NewsStory {
  implicit def jsonWrites = Json.writes[NewsStory]

  implicit def jsonReads = Json.reads[NewsStory]
}