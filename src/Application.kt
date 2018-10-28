package com.amichal2.brief

import com.fasterxml.jackson.databind.SerializationFeature
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.*
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.jackson.jackson
import io.ktor.request.httpVersion
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.Routing


fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
    install(DefaultHeaders)

    install(CallLogging)

    install(ForwardedHeaderSupport)

    install(ContentNegotiation) {
        jackson {
            enable(SerializationFeature.INDENT_OUTPUT)
        }
    }

    install(Routing) {
        get("/") {
            val httpVersion = call.request.httpVersion
            val clientIp = call.request.origin.remoteHost
            val paramValue = call.request.queryParameters["param"]
            call.respondText(
                "Request from $clientIp, http version $httpVersion, param value $paramValue",
                ContentType.Text.Html
            )
        }
        get("/data") {
            call.respond(HttpStatusCode.Created, ResponseInfo("ok", 200))
        }
    }
}

data class ResponseInfo(val info: String, val status: Int)
