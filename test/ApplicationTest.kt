package com.amichal2.brief

import io.ktor.config.MapApplicationConfig
import io.ktor.http.*
import io.ktor.server.testing.*
import io.ktor.util.KtorExperimentalAPI
import kotlin.test.*
import org.junit.Test

@KtorExperimentalAPI
class ApplicationTest {

    @Ignore
    @Test
    fun testRequests() = withTestApplication({
        (environment.config as MapApplicationConfig).apply {
            put("ktor.upstream.url", "https://content.guardianapis.com")
        }
        briefModule()
    }) {
        with(handleRequest(HttpMethod.Get, "/data")) {
            assertEquals(HttpStatusCode.Created, response.status())
        }
    }
}
