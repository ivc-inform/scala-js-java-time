val scalaJSVersion = Option(System.getenv("SCALAJS_VERSION")).getOrElse("0.6.20")

addSbtPlugin("org.scala-js" % "sbt-scalajs" % scalaJSVersion)

addSbtPlugin("org.scalastyle" %% "scalastyle-sbt-plugin" % "1.0.0")
