package com.amichal2.brief.service

import com.amichal2.brief.client.BriefClientImpl
import com.amichal2.brief.model.ContentResponse

interface BriefService {
    suspend fun getContent(): ContentResponse
}

class BriefServiceImpl(private val briefClient: BriefClientImpl) : BriefService {

    override suspend fun getContent(): ContentResponse {
        val article = briefClient.getGuardianContent().filter { it.fields.wordcount.toInt() in 100..500 }.first()
        return ContentResponse(article.fields.headline.toUpperCase() + "    " + article.fields.bodyText)
    }
}
