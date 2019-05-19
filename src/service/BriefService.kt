package com.amichal2.brief.service

import com.amichal2.brief.client.BriefClientImpl
import com.amichal2.brief.model.ContentResponse

interface BriefService {
    suspend fun getContent(query: String, url: String): ContentResponse
}

class BriefServiceImpl : BriefService {
    override suspend fun getContent(query: String, url: String): ContentResponse {
        val briefClient = BriefClientImpl()
        return(briefClient.getContent(query, url))
    }
}
