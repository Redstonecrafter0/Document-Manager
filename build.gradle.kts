plugins {
}

group = "net.redstonecraft"
version = "0.0.1"

tasks {
    task("buildDockerImage") {
        doLast {
            exec {
                commandLine("docker", "build", ".", "-t", "redstonecrafter0/document-manager:$version", "-t", "redstonecrafter0/document-manager")
            }
        }
    }

    register("buildProject") {
        dependsOn(subprojects.first { it.name == "Server" }.tasks.findByName("buildServer")!!)
        finalizedBy("buildDockerImage")
    }
}
