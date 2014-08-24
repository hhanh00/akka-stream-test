import scalabuff._

scalaVersion := "2.11.2"

libraryDependencies ++= Seq(
	"com.typesafe.akka" % "akka-stream-experimental_2.11" % "0.6",
	"net.sandrogrzicic" %% "scalabuff-runtime" % "1.3.9"
	)


lazy val root = Project("hello", file("."), settings = Defaults.defaultSettings ++ scalabuffSettings
	++ Seq(scalabuffArgs := Seq("--target=2.11.2"))).configs(ScalaBuff)
