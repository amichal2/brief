package com.amichal2.brief

import com.amichal2.brief.client.BriefClient
import com.amichal2.brief.client.BriefClientImpl
import com.amichal2.brief.model.UnexpectedResponseException
import com.amichal2.brief.repository.MongoDbService
import com.amichal2.brief.repository.MongoDbServiceImpl
import com.amichal2.brief.resource.briefRouting
import com.amichal2.brief.service.BriefService
import com.amichal2.brief.service.BriefServiceImpl
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.SerializationFeature
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.*
import io.ktor.http.HttpStatusCode
import io.ktor.jackson.jackson
import io.ktor.response.respond
import io.ktor.routing.routing
import io.ktor.util.KtorExperimentalAPI
import org.koin.dsl.module
import org.koin.experimental.builder.singleBy
import org.koin.ktor.ext.Koin
import org.koin.ktor.ext.inject

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

val koinModule = module {
    singleBy<BriefService, BriefServiceImpl>()
    singleBy<BriefClient, BriefClientImpl>()
    singleBy<MongoDbService, MongoDbServiceImpl>()
}

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
        }
    }

    install(Koin) {
        modules(koinModule)
    }

    val briefService: BriefService by inject()
    val mongoDbService: MongoDbService by inject()

    routing {
        briefRouting(briefService, mongoDbService)
    }
}
