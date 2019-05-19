package com.amichal2.brief

import com.amichal2.brief.model.UnexpectedResponseException
import com.amichal2.brief.resource.briefRouting
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.module.kotlin.KotlinModule
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.*
import io.ktor.http.HttpStatusCode
import io.ktor.jackson.jackson
import io.ktor.response.respond
import io.ktor.routing.routing
import io.ktor.util.KtorExperimentalAPI

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@KtorExperimentalAPI
fun Application.briefModule() {
    install(StatusPages) {
        exception<Throwable> {
            call.respond(HttpStatusCode.BadGateway, UnexpectedResponseException("detailed message:: ${it.localizedMessage}"))
        }
    }

    install(DefaultHeaders)

    install(CallLogging)

    install(ForwardedHeaderSupport)

    install(ContentNegotiation) {
        jackson {
            enable(SerializationFeature.INDENT_OUTPUT)
            configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            registerModule(KotlinModule())
        }
    }

    routing {
        briefRouting()
    }
}
