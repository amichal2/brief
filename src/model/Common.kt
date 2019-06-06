package com.amichal2.brief.model

data class PingRequest(val query: String?, val rating: Int)

data class ContentResponse(val content: String)

data class GuardianResponse(val response: GuardianResponseContent)

data class GuardianResponseContent(val results: List<Result>)

data class Result(val fields: Fields)

data class Fields(val bodyText: String, val headline: String, val wordcount: String)

data class UnexpectedResponseException(val message: String)
