

lazy val root = (project in file(".")).
  settings(
    name := "sbt-rocks",
    version := "0.2.0",
    organization := "io.haitch",
    scalaVersion := "2.10.6",
    sbtPlugin := true,
    sbtVersion := "0.13.11"
  ).enablePlugins(io.haitch.SbtRocks)

