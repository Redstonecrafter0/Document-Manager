import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project

plugins {
    application
    kotlin("jvm") version "1.7.10"
    id("org.jetbrains.kotlin.plugin.serialization") version "1.7.10"
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

group = "net.redstonecraft.documentmanager"
version = "0.0.1"

application {
    mainClass.set("net.redstonecraft.documentmanager.ApplicationKt")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/ktor/eap")
}

dependencies {
    implementation("io.ktor:ktor-server-core-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-auth-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-auth-jwt-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-host-common-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-compression-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-call-logging-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-content-negotiation-jvm:$ktor_version")
    implementation("io.ktor:ktor-serialization-kotlinx-json-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-netty-jvm:$ktor_version")
    implementation("ch.qos.logback:logback-classic:$logback_version")
    implementation("org.bouncycastle:bcpkix-jdk15on:1.70")
}

tasks {
    withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "17"
    }

    register<Copy>("copyWebUI") {
        dependsOn(parent!!.subprojects.first { it.name == "WebUI" }.tasks.findByName("npmBuild"))

        from(parent!!.subprojects.first { it.name == "WebUI" }.projectDir.resolve("dist/web-ui"))
        into(projectDir.resolve("src/main/resources/static"))
    }

    register("buildServer") {
        dependsOn("copyWebUI")
        finalizedBy("shadowJar")
    }
}
