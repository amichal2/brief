package com.amichal2.brief.resource

import com.amichal2.brief.model.PingRequest
import com.amichal2.brief.service.BriefServiceImpl
import io.ktor.application.application
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Routing
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.util.KtorExperimentalAPI
import java.lang.RuntimeException

@KtorExperimentalAPI
fun Routing.briefRouting() {
    post("/ping") {
        val received = call.receive<PingRequest>()
        call.respond(HttpStatusCode.Accepted, PingRequest(received.query + " from response with rating", received.rating * 10))
    }

    get("/content") {
        val queryParam = call.request.queryParameters["query"] ?: throw RuntimeException("query parameter is not present")
        val url = application.environment.config.propertyOrNull("ktor.upstream.url")?.getString() ?: throw RuntimeException("host not defined")
        val briefService = BriefServiceImpl()

        call.respond(HttpStatusCode.OK, briefService.getContent(queryParam, url))
    }
}
