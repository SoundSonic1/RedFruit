package com.example.redfruit.data.model.media

import android.os.Parcelable
import com.example.redfruit.data.model.interfaces.ISource
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

/**
 * Streamable video via dash url
 */
@JsonClass(generateAdapter = true)
@Parcelize
data class RedditVideo(
    @Json(name = "dash_url")
    override val url: String = "",
    val height: Int,
    val width: Int
) : ISource, Parcelable
