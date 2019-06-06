package com.amichal2.brief.service

import com.amichal2.brief.client.BriefClientImpl

interface BriefService {
    suspend fun getContent(): String
}

class BriefServiceImpl(private val briefClient: BriefClientImpl) : BriefService {

    override suspend fun getContent(): String {
        val article = briefClient.getGuardianContent().first { it.fields.wordcount.toInt() in 100..500 }
        return article.fields.headline.toUpperCase() + "    " + article.fields.bodyText
    }
}
