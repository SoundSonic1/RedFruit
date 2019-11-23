package com.example.redfruit.data.model

import com.example.redfruit.data.model.interfaces.ICreated
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Comment(
    val author: String = "Unknown",
    val body: String = "",
    override val created: Long = 0,
    override val created_utc: Long = 0,
    val gildings: Gildings,
    val id: String = "",
    val score: Int = 0,
    val replies: List<Comment> = listOf(),
    val depth: Int = 0
) : ICreated
