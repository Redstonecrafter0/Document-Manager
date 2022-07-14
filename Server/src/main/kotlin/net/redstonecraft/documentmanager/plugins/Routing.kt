package net.redstonecraft.documentmanager.plugins

import io.ktor.server.routing.*
import io.ktor.server.http.content.*
import io.ktor.server.application.*
import io.ktor.server.response.*

fun Application.configureRouting() {
    routing {
        get("/api") {
            call.respondText("Hello World!")
        }
        static("/") {
            resources("static")
            defaultResource("index.html", "static")
        }
    }
}
