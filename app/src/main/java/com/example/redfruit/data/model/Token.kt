package com.example.redfruit.data.model

import com.google.gson.annotations.SerializedName

/**
 * Represents access/refresh token
 */
data class Token(
    @SerializedName("access_token")
    val access: String = "",
    @SerializedName("token_type")
    val type: String = "",
    val expires_in: Long = 0
)