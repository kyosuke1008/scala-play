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


  val form = Form(
    "word" -> text
  )

  /**
   * index
   * @return index
   */
  def index = Action {
    val futureResponse: Future[WSResponse] =
      WS.url("http://localhost:8983/solr/solrbook/select?q=*%3A*&sort=updated+desc&rows=50&wt=json&indent=true").get()
    val (stories, numFound) = makeJson(futureResponse)
    Ok(views.html.index(stories, numFound)).withSession(
      "word" -> "")
  }


  /**
   * 検索結果
   * @return 検索結果
   */
  def select = Action { implicit request =>
    val selectWord = form.bindFromRequest.get
    val word = StringUtil.createFp(selectWord)
    val futureResponse: Future[WSResponse] =
      WS.url("http://localhost:8983/solr/solrbook/select?q=*%3A*&sort=updated+desc" + word + "&rows=50&wt=json&indent=true").get()
    val (stories, numFound) = makeJson(futureResponse)
    Ok(views.html.index(stories, numFound)).withSession(
      "word" -> word)
  }

  /**
   * ページ検索
   * @return 検索結果
   */
  def page(p: Int) = Action { implicit request =>
    val word = request.session.get("word").get
    print("あああああああああああああああああ"+word)
    val futureResponse: Future[WSResponse] =
      WS.url("http://localhost:8983/solr/solrbook/select?q=*%3A*&sort=updated+desc" + word + "&rows=50&wt=json&indent=true").get()
    val (stories, numFound) = makeJson(futureResponse)
    Ok(views.html.index(stories, numFound)).withSession(
      "selectWord" -> word)
  }


  /**
   *
   * @param futureResponse json,numFound
   * @return json
   */
  def makeJson(futureResponse: Future[WSResponse]) = {
    val result = Await.result(futureResponse, Duration.Inf)
    val json: JsValue = Json.parse(result.body)
    val title: Seq[JsValue] = json \\ "title"
    val numFound = json \ "response" \ "numFound"
    val stories = title.indices.toList.map(i => NewsStory(
      (json \\ "info_url")(i).toString()
      , (json \\ "url")(i).toString()
      , (json \\ "title")(i).toString()
      , (json \\ "info_title")(i).toString()
      , ""
      , (json \\ "summary")(i).toString()
      , (json \\ "intended_reader")(i).toString()
      , (json \\ "updated")(i).toString()))
    (stories, numFound.get.as[Int])
  }
}
