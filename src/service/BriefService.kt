package com.amichal2.brief.service

import com.amichal2.brief.client.BriefClientImpl
import com.amichal2.brief.model.ContentResponse

interface BriefService {
    suspend fun getContent(): ContentResponse
}

class BriefServiceImpl(private val briefClient: BriefClientImpl) : BriefService {

    override suspend fun getContent(): ContentResponse {
        val articles = briefClient.getGuardianContent()
        for (result in articles) {
            println("plain text " + result.fields.bodyText)
        }
        return ContentResponse(articles[0].fields.bodyText)
    }
}
