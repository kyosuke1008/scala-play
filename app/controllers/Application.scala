package controllers

import model.NewsStory
import play.api.Play.current
import play.api.data.Form
import play.api.data.Forms._
import play.api.libs.json.{JsValue, Json}
import play.api.libs.ws.{WS, WSResponse}
import play.api.mvc._
import util.StringUtil

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}


class Application extends Controller {

  /**
   * index
   * @return index
   */
  def index = Action {
    val futureResponse: Future[WSResponse] =
      WS.url("http://localhost:8983/solr/solrbook/select?q=*%3A*&rows=50&wt=json&indent=true").get()
    Ok(views.html.index(makeJson(futureResponse)))
  }

  val form = Form("word" -> text)

  /**
   * 検索結果
   * @return 検索結果
   */
  def select = Action { implicit request =>
    val selectWord = StringUtil.createFp(form.bindFromRequest.get)
    print(form.bindFromRequest.get)
    val futureResponse: Future[WSResponse] =
      WS.url("http://localhost:8983/solr/solrbook/select?q=*%3A*" + selectWord + "&rows=50&wt=json&indent=true").get()
    Ok(views.html.index(makeJson(futureResponse)))
  }

  /**
   *
   * @param futureResponse
   * @return json
   */
  def makeJson(futureResponse: Future[WSResponse]) = {
    val result: WSResponse = Await.result(futureResponse, Duration.Inf)
    val json: JsValue = Json.parse(result.body)
    val title: Seq[JsValue] = json \\ "title"
    title.indices.toList.map(i => NewsStory(
      (json \\ "info_url")(i).toString()
      , (json \\ "url")(i).toString()
      , (json \\ "title")(i).toString()
      , (json \\ "info_title")(i).toString()
      , ""
      , (json \\ "summary")(i).toString()
      , (json \\ "intended_reader")(i).toString()
      , (json \\ "updated")(i).toString()))
  }
}
