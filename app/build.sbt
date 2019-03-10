scalaVersion := "2.12.7"

libraryDependencies ++= Seq(
    "org.scalaz.stream" %% "scalaz-stream" % "0.8.6",
    "org.scalactic" %% "scalactic" % "3.0.5",
    "org.scalatest" %% "scalatest" % "3.0.5" % "test"
)

scalacOptions ++= Seq(
    "-feature",
    "-deprecation"
)
