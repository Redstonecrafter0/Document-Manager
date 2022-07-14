package net.redstonecraft.documentmanager

import io.ktor.server.engine.*
import io.ktor.server.netty.*
import net.redstonecraft.documentmanager.plugins.*
import org.bouncycastle.jce.provider.BouncyCastleProvider
import java.security.Security

fun main() {
    Security.addProvider(BouncyCastleProvider())
    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        configureRouting()
        configureSecurity()
        configureHTTP()
        configureMonitoring()
        configureSerialization()
    }.start(wait = true)
}
