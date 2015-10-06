package schedule

/**
 * @author ishizukyousuke on 2015/10/03.
 */

import java.net.URL

import com.sun.syndication.feed.synd.{SyndEntry, SyndFeed}
import com.sun.syndication.io.{SyndFeedInput, XmlReader}
import model.NewsStory
import play.api.Play.current
import play.api.libs.json.Json
import play.api.libs.ws.WS

import scala.collection.convert.WrapAsScala._

class SampleActor1 extends ActorBase {

  /** 処理本体 */
  def execute() = {
    print("取り込みバッチスタート")
    val url: String = "http://news020.blog13.fc2.com/?xml"
    val input: SyndFeedInput = new SyndFeedInput
    val feed: SyndFeed = input.build(new XmlReader(new URL(url)))
    implicit val univWrites = Json.writes[NewsStory]

    val list: List[NewsStory] = feed.getEntries.map(entry => {
      val e: SyndEntry = entry.asInstanceOf[SyndEntry]
      NewsStory(e.getLink, feed.getLink, feed.getTitle, e.getTitle, e.getTitle, e.getDescription.getValue, feed.getTitle,
        "%tY%<tm%<td" format e.getPublishedDate)
    }
    ).toList
    WS.url("http://localhost:8983/solr/solrbook/update/json?commit=true")
      .withHeaders("Content-Type" -> "application/json").post(Json.toJson(list))
  }
}
