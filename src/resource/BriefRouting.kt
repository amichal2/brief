package com.amichal2.brief.resource

import com.amichal2.brief.model.PingRequest
import com.amichal2.brief.repository.MongoDbService
import com.amichal2.brief.service.BriefService
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
fun Routing.briefRouting(briefService: BriefService, mongoDbService: MongoDbService) {

    post("/ping") {
        val pingRequest = call.receive<PingRequest>()
        call.respond(HttpStatusCode.Accepted, PingRequest(pingRequest.query + " from response with rating", pingRequest.rating * 10))
    }

    get("/content") {
        val queryParam = call.request.queryParameters["query"] ?: throw RuntimeException("query parameter is not present")
        call.respond(HttpStatusCode.OK, briefService.getContent(queryParam))
    }

    get("/collection") {
        call.respond(HttpStatusCode.OK, mongoDbService.retrieveCollectionData())
    }
}
