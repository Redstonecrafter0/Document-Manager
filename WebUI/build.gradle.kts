plugins {
}

group = "net.redstonecraft"
version = "0.0.1"

val npmCommand = if ("windows" in System.getProperty("os.name").toLowerCase()) "npm.cmd" else "npm"

tasks {
  task<Exec>("npmServe") {
    commandLine(npmCommand, "run", "start")
  }

  task<Exec>("npmBuild") {
    commandLine(npmCommand, "run", "build")
  }

  task<Exec>("npmTest") {
    commandLine(npmCommand, "run", "test")
  }
}
