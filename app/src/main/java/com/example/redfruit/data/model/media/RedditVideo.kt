package com.example.redfruit.data.model.media

import com.example.redfruit.data.model.interfaces.ISource
import com.google.gson.annotations.SerializedName

/**
 * Note a reddit video does not contain any sound
 */
data class RedditVideo(
    @SerializedName("fallback_url")
    override val url: String,
    val height: Int,
    val width: Int
) : ISource
