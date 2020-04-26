package com.amichal2.brief.client

import com.amichal2.brief.model.GuardianResponse
import com.amichal2.brief.model.Result
import com.fasterxml.jackson.databind.DeserializationFeature
import com.typesafe.config.ConfigFactory
import io.ktor.client.HttpClient
import io.ktor.client.engine.apache.Apache
import io.ktor.client.features.json.JacksonSerializer
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.request.get
import io.ktor.config.HoconApplicationConfig
import io.ktor.util.KtorExperimentalAPI
import org.apache.http.client.utils.URIBuilder
import java.time.LocalDate
import java.time.format.DateTimeFormatter

interface BriefClient {
    suspend fun getGuardianContent(query: String): List<Result>
}

class BriefClientImpl : BriefClient {

    @KtorExperimentalAPI
    override suspend fun getGuardianContent(query: String): List<Result> {

        val config = HoconApplicationConfig(ConfigFactory.load())
        val url = config.property("ktor.upstream.url").getString()
        val apiKey = config.property("ktor.upstream.apiKey").getString()

        val client = HttpClient(Apache) {
            install(JsonFeature) {
                serializer = JacksonSerializer {
                    configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                }
            }
        }

        val uriBuilder = URIBuilder(url)
            .setPath("/search")
            .addParameter("q", query)
//            .addParameter("order-by", "newest")
            .addParameter("show-fields", "all")
//            .addParameter("from-date", LocalDate.now().format(DateTimeFormatter.ISO_DATE))
            .addParameter("page-size", "100")
            .addParameter("api-key", apiKey)

        val guardianResponse = client.get<GuardianResponse>(uriBuilder.build().toString())
        client.close()
        return guardianResponse.response.results
    }
}
