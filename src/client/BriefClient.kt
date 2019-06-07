package com.amichal2.brief.client

import com.amichal2.brief.model.GuardianResponse
import com.amichal2.brief.model.Result
import com.fasterxml.jackson.databind.DeserializationFeature
import io.ktor.client.HttpClient
import io.ktor.client.engine.apache.Apache
import io.ktor.client.features.json.JacksonSerializer
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.request.get
import org.apache.http.client.utils.URIBuilder
import java.time.LocalDate
import java.time.format.DateTimeFormatter

interface BriefClient {
    suspend fun getGuardianContent(): List<Result>
}

class BriefClientImpl(private val query: String, private val url: String, private val apiKey: String) : BriefClient {

    override suspend fun getGuardianContent(): List<Result> {
        val client = HttpClient(Apache) {
            install(JsonFeature) {
                serializer = JacksonSerializer {
                    configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                }
            }
            engine {
                response.apply {
                    defaultCharset = Charsets.UTF_8
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

        println("tmp print: " + uriBuilder.build().toString())

        val guardianResponse = client.get<GuardianResponse>(uriBuilder.build().toString())
        client.close()
        return guardianResponse.response.results
    }
}
