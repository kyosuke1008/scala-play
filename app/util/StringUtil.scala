package util

import java.text.SimpleDateFormat

/**
 * @author ishizukyousuke on 2015/10/07.
 */
object StringUtil {

  /**
   *
   * @param str
   * @return
   */
  def changeHtmlString(str: String) = {
    str.replaceAllLiterally("\\n", "").replaceAllLiterally("\"]", "").replaceAllLiterally("[\"", "")
  }

  /**
   *
   * @param str
   * @return
   */
  def deleteQuot(str: String) = {
    str.replaceAllLiterally("\"", "")
  }

  /**
   *
   * @param str
   * @return
   */
  def changeDateFormat(str: String) = {
    val sdf: SimpleDateFormat = new SimpleDateFormat("yyyyMMdd");
    "%tY年%<tm月%<td日" format sdf.parse(str.replaceAllLiterally("\"", ""))
  }

  /**
   *
   * @param str
   * @return
   */
  def createFp(str: String) = {
    val strs: List[String] = str.split(" ").toList
    strs.map(s => "&fq=" + s).mkString
  }
}
