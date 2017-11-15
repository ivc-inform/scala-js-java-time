scalaVersion in ThisBuild := "2.12.4"

val commonSettings: Seq[Setting[_]] = Seq(
    version := "0.2.3-SNAPSHOT",
    organization := "org.scala-js",
    scalacOptions ++= Seq("-deprecation", "-feature", "-Xfatal-warnings")
)

lazy val root: Project = project.in(file(".")).
  enablePlugins(ScalaJSPlugin).
  settings(commonSettings).
  settings(
      name := "scalajs-java-time",

      mappings in(Compile, packageBin) ~= {
          _.filter(!_._2.endsWith(".class"))
      },
      exportJars := true,

      publishMavenStyle := true,
      publishTo := {
          val corporateRepo = "http://toucan.simplesys.lan/"
          if (isSnapshot.value)
              Some("snapshots" at corporateRepo + "artifactory/libs-snapshot-local")
          else
              Some("releases" at corporateRepo + "artifactory/libs-release-local")
      },
      credentials += Credentials(Path.userHome / ".ivy2" / ".credentials"),
      pomIncludeRepository := { _ => false }
  )

lazy val testSuite = crossProject.
  jsConfigure(_.enablePlugins(ScalaJSJUnitPlugin)).
  settings(commonSettings: _*).
  settings(
      testOptions +=
        Tests.Argument(TestFramework("com.novocode.junit.JUnitFramework"), "-v", "-a")
  ).
  jsSettings(
      name := "java.time testSuite on JS"
  ).
  jsConfigure(_.dependsOn(root)).
  jvmSettings(
      name := "java.time testSuite on JVM",
      libraryDependencies +=
        "com.novocode" % "junit-interface" % "0.9" % "test"
  )

lazy val testSuiteJS = testSuite.js
lazy val testSuiteJVM = testSuite.jvm
