
name                :=  """karmaCounter"""
organization        :=  """com.orendainx"""
version             :=  """1.0.0"""

scalaVersion        :=  """2.11.6"""

scalacOptions       :=  Seq("-unchecked", "-deprecation", "-feature", "-encoding", "utf8")

libraryDependencies ++= {
  val akkaV         =  "2.3.12"
  val akkaStreamV   =  "1.0-RC3"

  Seq(
    //"com.typesafe.akka"  %% "akka-actor"                  % akkaV,
    "com.typesafe.akka"  %% "akka-stream-experimental"    % akkaStreamV,
    "com.typesafe.akka"  %% "akka-http-core-experimental" % akkaStreamV,
    "com.typesafe.akka"  %% "akka-http-experimental"      % akkaStreamV,
    
    //"com.orendain"  % "sraw"      % "0.1",
    "default"  %% "sraw"      % "0.1"
  )
}
