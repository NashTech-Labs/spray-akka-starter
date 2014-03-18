import AssemblyKeys._ // put this at the top of the file
import de.johoop.jacoco4sbt._
import JacocoPlugin._

name:="hub-n-spoke"

version:="1.0"

scalaVersion:="2.10.3"

scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "utf8")


resolvers ++= Seq(
  "spray repo" at "http://repo.spray.io/",
  "spray nightly repo" at "http://nightlies.spray.io",
   "opencast-public" at "http://repository.opencastproject.org/nexus/content/repositories/public/"
)


// Add multiple dependencies
libraryDependencies ++= Seq(
	"com.typesafe.akka" 		%% 	"akka-actor" 		%	"2.2.3",
	"com.typesafe.akka" 		%%	"akka-remote" 		% 	"2.2.3",
	"com.typesafe.akka" 		%%	"akka-kernel" 		% 	"2.2.3",
	"com.typesafe.akka"			%% 	"akka-cluster" 		% 	"2.2.3",
	"com.typesafe.akka"     	%%  "akka-slf4j"        %   "2.2.3",
	"com.typesafe.akka"     	%% 	"akka-contrib"      %   "2.2.3",
  	"io.spray" 					% 	"spray-routing" 	% 	"1.2-20131004",
	"io.spray" 					% 	"spray-can" 		% 	"1.2-20131004",
  	"io.spray" 					%% 	"spray-json"		% 	"1.2.5",
  	"io.spray" 					% 	"spray-http"		% 	"1.2-20131004",
	"ch.qos.logback" 			% 	"logback-classic" 	% 	"1.0.13",
  	"org.specs2"          		%%  "specs2"        	% 	"1.14" % "test",
  	"io.spray" 					% 	"spray-testkit" 	% 	"1.2-20131004"	%	"test",
	"com.typesafe.akka" 		%% 	"akka-testkit" 		% 	"2.2.3"	%	"test",
	"junit" 					% 	"junit" 			% 	"4.11"	%	"test",
	"org.scalatest" 			%% 	"scalatest" 		% 	"1.9.2"	%	"test"
)

test in assembly := {}

org.scalastyle.sbt.ScalastylePlugin.Settings

seq(Revolver.settings: _*)

seq(jacoco.settings : _*)

seq(ScctPlugin.instrumentSettings : _*)

