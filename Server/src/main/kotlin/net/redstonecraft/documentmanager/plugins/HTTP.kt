package net.redstonecraft.documentmanager.plugins

import io.ktor.server.plugins.compression.*
import io.ktor.server.application.*

fun Application.configureHTTP() {
    install(Compression) {
        gzip {
            priority = 10.0
        }
        deflate {
            priority = 1.0
            minimumSize(1024)
        }
    }

}
