name := "tictactoe"

version := "1.0"

scalaVersion := "2.12.4"


libraryDependencies ++= Seq(
  "com.typesafe" % "config" % "1.3.2",
  "org.scalatest" %% "scalatest" % "3.0.0" % "test",
  "org.scalamock" %% "scalamock-scalatest-support" % "3.5.0" % Test
)

