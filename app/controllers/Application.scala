package controllers

import com.google.cloud.bigquery.{BigQueryOptions, DatasetInfo}
import play.api.data.Form
import play.api.data.Forms._
import play.api.mvc._


class Application extends Controller {

  val form = Form(
    "word" -> text
  )

  /**
   * index
   * @return index
   */
  def index = Action {

    // Instantiates a client
    val bigquery = BigQueryOptions.getDefaultInstance.getService

    // The name for the new dataset
    val datasetName = "my_new_dataset"

    // Prepares a new dataset
    val datasetInfo = DatasetInfo.newBuilder(datasetName).build()

    // Creates the dataset
    val dataset = bigquery.create(datasetInfo)

    System.out.printf("Dataset %s created.%n", dataset.getDatasetId.getDataset)
    Ok(views.html.index("")).withSession(
      "word" -> "")
  }
}
