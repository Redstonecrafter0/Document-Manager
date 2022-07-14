package net.redstonecraft.document_manager

import io.ktor.server.engine.*
import io.ktor.server.netty.*
import net.redstonecraft.document_manager.plugins.*

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        configureRouting()
        configureSecurity()
        configureHTTP()
        configureMonitoring()
        configureSerialization()
    }.start(wait = true)
}
