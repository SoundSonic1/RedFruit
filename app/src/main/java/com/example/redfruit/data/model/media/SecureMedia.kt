package com.example.redfruit.data.model.media

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

/**
 * Only HTTPS allowed
 */
@JsonClass(generateAdapter = true)
@Parcelize
data class SecureMedia(
    val redditVideo: RedditVideo?,
    val youtubeoEmbed: YoutubeoEmbed?
) : Parcelable
