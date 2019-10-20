package com.example.redfruit.data.model

import com.beust.klaxon.Json

/**
 * Represents access/refresh token
 */
data class Token(
    @Json(name = "access_token")
    val access: String = "",
    @Json(name = "token_type")
    val type: String = "",
    val expires_in: Long = 0
)