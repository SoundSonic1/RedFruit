package com.example.redfruit.data.model

import com.beust.klaxon.Json

/**
 * Stores given awards to posts/comments
 */
data class Gildings(
    @Json(name = "gid_1")
    val silverCount: Int = 0,
    @Json(name = "gid_2")
    val goldCount: Int = 0,
    @Json(name = "gid_3")
    val platinumCount: Int = 0
)
