import sbt._
import sbt.Keys._

lazy val buildSettings = Seq(
  organization := "com.meetup",
  scalaVersion := "2.12.1",
  licenses += ("MIT", url("http://opensource.org/licenses/MIT")),
  homepage := Some(url("http://github.com/meetup/archery")),
  version := "0.5.0-SNAPSHOT",
  crossScalaVersions := Seq("2.11.8", "2.12.1"))

lazy val commonSettings = Seq(
  scalacOptions ++= Seq(
    "-deprecation",
    "-encoding", "UTF-8",
    "-feature",
    "-language:existentials",
    "-language:higherKinds",
    "-language:implicitConversions",
    "-language:experimental.macros",
    "-unchecked",
    "-Xfatal-warnings",
    "-Xlint",
    //"-Yinline-warnings",
    //"-Yno-adapted-args",
    "-Ywarn-dead-code",
    //"-Ywarn-numeric-widen",
    //"-Ywarn-value-discard",
    "-Xfuture"))

lazy val publishSettings = Seq(
  bintrayOrganization := Some("meetup"))

lazy val noPublishSettings = Seq(
  publish := (),
  publishLocal := (),
  publishArtifact := false)

lazy val archerySettings =
  buildSettings ++ commonSettings ++ publishSettings

lazy val archery =
  project.in(file("."))
  .settings(moduleName := "aggregate")
  .settings(archerySettings)
  .settings(noPublishSettings)
  .aggregate(core, benchmark)

lazy val core =
  project
  .settings(moduleName := "archery")
  .settings(archerySettings)
  .settings(libraryDependencies ++= Seq(
    "org.scalatest" %% "scalatest" % "3.0.1" % "test",
    "org.scalacheck" %% "scalacheck" % "1.13.4" % "test"))

lazy val benchmark =
  project.dependsOn(core)
  .settings(moduleName := "archery-benchmark")
  .settings(archerySettings)
  .settings(noPublishSettings)
  .settings(Seq(
    fork in run := true,
    javaOptions in run += "-Xmx4G",
    libraryDependencies ++= Seq(
      "com.github.ichoran" %% "thyme" % "0.1.2-SNAPSHOT"),
    resolvers += Resolver.sonatypeRepo("snapshots")))
