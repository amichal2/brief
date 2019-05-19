package com.amichal2.brief.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

data class PingRequest(val query: String?, val rating: Int)

data class ContentResponse(val content: String, val rating: Int)

@JsonIgnoreProperties(ignoreUnknown = true)
data class GuardianResponse(val response: GuardianResponseContent)

@JsonIgnoreProperties(ignoreUnknown = true)
data class GuardianResponseContent(val results: List<Result>)

@JsonIgnoreProperties(ignoreUnknown = true)
data class Result(val fields: Fields)

@JsonIgnoreProperties(ignoreUnknown = true)
data class Fields(val bodyText: String, val wordcount: String)

data class UnexpectedResponseException(val message: String)
