package com.example.redfruit.data.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Comment(
    val author: String = "Unknown",
    val body: String = "",
    val created: Long = 0,
    val created_utc: Long = 0,
    val gildings: Gildings,
    val id: String = "",
    val score: Int = 0,
    val replies: List<Comment> = listOf(),
    val depth: Int = 0
)
