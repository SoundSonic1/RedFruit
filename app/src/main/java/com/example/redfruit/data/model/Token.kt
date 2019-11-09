package com.example.redfruit.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Represents access/refresh token
 */
@JsonClass(generateAdapter = true)
data class Token(
    @Json(name = "access_token")
    val access: String = "",
    @Json(name = "token_type")
    val type: String = "",
    val expires_in: Long = 0
)
