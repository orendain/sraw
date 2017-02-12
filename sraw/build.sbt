
name := """SRAW"""

version := "0.1"

scalaVersion := "2.11.6"

// http://www.scala-sbt.org/0.13.5/docs/Howto/metadata.html

//resolvers += "spray repo" at "http://repo.spray.io"

scalacOptions in (Compile,doc) ++= Seq("-groups", "-implicits")

//scalacOptions in (Compile, doc) := Seq("-doc-root-content", baseDirectory.value+"/root-doc.txt")

libraryDependencies ++= {
  val akkaV       = "2.3.12"
  val akkaStreamV = "1.0-RC3"
  val scalaTestV  = "2.2.4"

  Seq(
    "com.typesafe.akka" %% "akka-actor"                        % akkaV,
    "com.typesafe.akka" %% "akka-stream-experimental"          % akkaStreamV,
    "com.typesafe.akka" %% "akka-http-core-experimental"       % akkaStreamV,
    "com.typesafe.akka" %% "akka-http-experimental"       % akkaStreamV,
    //"com.typesafe.akka" %% "akka-http-spray-json-experimental" % akkaStreamV,
    //"com.typesafe.akka" %% "akka-http-testkit-experimental"    % akkaStreamV,

    "com.typesafe.akka"  %% "akka-slf4j"                  % akkaV,
    "ch.qos.logback"     %  "logback-classic"             % "1.1.3",

    "org.json4s" %% "json4s-native" % "3.2.11",
    "org.scalatest"     %% "scalatest"                         % scalaTestV % "test"
  )
}

assemblyJarName in assembly := "SRAW-0.1.jar"
test in assembly := {}
