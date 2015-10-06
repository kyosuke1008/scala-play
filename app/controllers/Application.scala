package controllers

import model.NewsStory
import play.api.Play.current
import play.api.libs.json.{JsValue, Json}
import play.api.libs.ws.{WS, WSResponse}
import play.api.mvc._

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}

class Application extends Controller {

  /**
   *
   * @return
   */
  def index = Action {
    val futureResponse: Future[play.api.libs.ws.WSResponse] =
      WS.url("http://localhost:8983/solr/solrbook/select?q=*%3A*&rows=50&wt=json&indent=true").get()
    val result: WSResponse = Await.result(futureResponse, Duration.Inf)
    val json: JsValue = Json.parse(result.body)
    val title: Seq[JsValue] = json \\ "title"
    val newsStories: List[NewsStory] = title.indices.toList.map(i => NewsStory(
      (json \\ "info_url")(i).toString()
      , (json \\ "url")(i).toString()
      , (json \\ "title")(i).toString()
      , (json \\ "info_title")(i).toString()
      , ""
      , (json \\ "summary")(i).toString()
      , (json \\ "intended_reader")(i).toString()
      , (json \\ "updated")(i).toString()))
    Ok(views.html.index(newsStories))
  }
}
