name := """Summary"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.7"
resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"

libraryDependencies += "com.enragedginger" % "akka-quartz-scheduler_2.11" % "1.4.0-akka-2.3.x"

libraryDependencies ++= Seq(
  jdbc,
  cache,
  ws,
  specs2 % Test,
  evolutions,
  "rome" % "rome" % "1.0",
  "org.jdom" % "jdom" % "2.0.2",
  "org.scalatestplus" %% "play" % "1.4.0-M4" % "test",
  "com.google.cloud" % "google-cloud-bigquery" % "0.8.0-beta",
  "com.google.cloud" % "google-cloud-datastore" % "0.8.1-beta"

)
resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"

// Play provides two styles of routers, one expects its actions to be injected, the
// other, legacy style, accesses its statically.
name := "di-sample"

PlayKeys.playOmnidoc := false
routesGenerator := InjectedRoutesGenerator
