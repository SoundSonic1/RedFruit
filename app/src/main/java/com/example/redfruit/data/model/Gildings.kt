package com.example.redfruit.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Stores given awards to posts/comments
 */
@JsonClass(generateAdapter = true)
data class Gildings(
    @Json(name = "gid_1")
    val silverCount: Int = 0,
    @Json(name = "gid_2")
    val goldCount: Int = 0,
    @Json(name = "gid_3")
    val platinumCount: Int = 0
)
