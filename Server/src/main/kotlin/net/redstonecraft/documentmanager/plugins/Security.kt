package net.redstonecraft.documentmanager.plugins

import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.server.application.*
import org.bouncycastle.openssl.PEMKeyPair
import org.bouncycastle.openssl.PEMParser
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter
import java.io.File
import java.security.interfaces.ECPrivateKey
import java.security.interfaces.ECPublicKey

fun Application.configureSecurity() {

    authentication {
        jwt {
            val keyRootDir = File(System.getenv("TESTING_SECRETS_DIR") ?: "/secrets")
            val privateKeyFile = keyRootDir.resolve("jwt_private.pem")
//            val publicKeyFile = keyRootDir.resolve("jwt_public.pem")
//            if (publicKeyFile.exists() && publicKeyFile.isFile) {
//                publicKeyFile.delete()
//            }
            if (!privateKeyFile.exists() || !privateKeyFile.isFile) {
                Runtime.getRuntime().exec(arrayOf("openssl", "ecparam", "-name", "secp521r1", "-genkey", "-out", privateKeyFile.absolutePath)).waitFor()
            }
//            Runtime.getRuntime().exec(arrayOf("openssl", "ec", "-in", privateKeyFile.absolutePath, "-pubout", "-out", publicKeyFile.absolutePath)).waitFor()
            val privateKeyContent = privateKeyFile.readText()
            val privateKey = if ("-----BEGIN EC PARAMETERS-----" in privateKeyContent) {
                privateKeyContent.split("-----END EC PARAMETERS-----")[1]
            } else {
                privateKeyContent
            }
            val keyPair = JcaPEMKeyConverter().getKeyPair(PEMParser(privateKey.reader()).readObject() as PEMKeyPair)
            val jwtAudience = "TESTING"
            realm = "TESTING"
            verifier(
                JWT.require(Algorithm.ECDSA512(keyPair.public as ECPublicKey, keyPair.private as ECPrivateKey))
                    .withAudience(jwtAudience)
                    .withIssuer("TESTING_DOMAIN")
                    .build()
            )
            validate { credential ->
                if (credential.payload.audience.contains(jwtAudience)) JWTPrincipal(credential.payload) else null
            }
        }
    }

}
