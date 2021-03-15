package org.acme.api

data class LinkDto(
        val originalUrl: String,
        val shortUrl: String,
        val slug: String,
)
