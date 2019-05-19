package com.amichal2.brief.client

import com.amichal2.brief.model.ContentResponse
import com.amichal2.brief.model.GuardianResponse
import io.ktor.client.HttpClient
import io.ktor.client.engine.apache.Apache
import io.ktor.client.features.json.JacksonSerializer
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.request.get

interface BriefClient {
    suspend fun getContent(query: String, url: String): ContentResponse
}

class BriefClientImpl : BriefClient {
    override suspend fun getContent(query: String, url: String): ContentResponse {
        val client = HttpClient(Apache) {
            install(JsonFeature) {
                serializer = JacksonSerializer()
            }
        }
        val guardianResponse = client.get<GuardianResponse>("$url/search?q=$query&order-by=newest&show-fields=all&api-key=263b5c7d-02df-4865-aa7a-d2a3c73795f2")
        client.close()
        return ContentResponse(
            guardianResponse.response.results[0].fields.bodyText,
            guardianResponse.response.results[0].fields.wordcount.toInt()
        )
    }
}
