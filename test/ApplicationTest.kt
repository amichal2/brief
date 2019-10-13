package com.amichal2.brief

import io.ktor.application.Application
import io.ktor.http.*
import io.ktor.http.HttpHeaders
import io.ktor.server.testing.*
import io.ktor.util.KtorExperimentalAPI
import kotlin.test.*
import org.junit.Test

@KtorExperimentalAPI
class ApplicationTest {

    @Test
    fun testPingRequest() {
        withTestApplication(Application::briefModule) {
            handleRequest(HttpMethod.Post, "/ping") {
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody("{\n" +
                        "    \"query\" : \"football\",\n" +
                        "    \"rating\" : 5\n" +
                        "}")
            }.apply {
                assertEquals(HttpStatusCode.Accepted, response.status())
                assertEquals("{\n" +
                        "  \"query\" : \"football from response with rating\",\n" +
                        "  \"rating\" : 50\n" +
                        "}", response.content)
            }
        }
    }
}
