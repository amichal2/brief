package com.amichal2.brief.service

import com.amichal2.brief.client.BriefClient
import com.amichal2.brief.model.Result
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

const val PAID_ACCESS = "paid-members-only"

interface BriefService {
    suspend fun getContent(query: String): String
}

class BriefServiceImpl(private val briefClient: BriefClient) : BriefService {

    override suspend fun getContent(query: String): String {
        val articles = briefClient.getGuardianContent(query)
            .filter { it.fields.wordcount.toInt() in 100..500 }
        for (article in articles) {
            article.fields.membershipAccess?.let {
                if (PAID_ACCESS == it) {
                    return formatContent(article, "PAID")
                }
            }
        }
        return formatContent(articles[0])
    }

    private fun formatContent(article: Result, type: String = ""): String {
        val publishDate = LocalDateTime
            .parse(article.webPublicationDate, DateTimeFormatter.ISO_ZONED_DATE_TIME)
            .toLocalDate()
            .format(DateTimeFormatter.ISO_DATE)

        return "$type $publishDate  ${article.fields.headline.toUpperCase()}    ${article.fields.bodyText}"
    }
}
