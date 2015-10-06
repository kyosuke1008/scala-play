
/**
 * @author ishizukyousuke on 2015/10/03.
 */
import akka.actor.Props
import com.typesafe.akka.extension.quartz.QuartzSchedulerExtension
import play.api.Play.current
import play.api.libs.concurrent.Akka
import play.api.{Application, GlobalSettings}

object Global extends GlobalSettings {
  override def onStart(app: Application) {
    // Akka Actorのスケジューリング
    val scheduler = QuartzSchedulerExtension(Akka.system)
    scheduler.schedules.foreach {
      case (key, setting) =>
        scheduler.schedule(
          setting.name, // Actor名
          Akka.system.actorOf(Props(Class.forName(s"schedule.${setting.name}"))), // ActorRef
          setting.description.getOrElse(setting.name) // Actorに送信するメッセージ
        )
    }
  }
}