package util

import java.text.SimpleDateFormat

/**
 * @author ishizukyousuke on 2015/10/07.
 */
object StringUtil {

  def changeHtmlString(str: String): String = {
    str.replaceAllLiterally("\\n", "").replaceAllLiterally("\"]", "").replaceAllLiterally("[\"", "")
  }

  def deleteQuot(str: String): String = {
    str.replaceAllLiterally("\"", "")
  }

  def changeDateFormat(str: String): String = {
    val sdf: SimpleDateFormat = new SimpleDateFormat("yyyyMMdd");
    "%tY年%<tm月%<td日" format sdf.parse(str.replaceAllLiterally("\"", ""))
  }
}
