package com.example.redfruit.data.model.media

import android.os.Parcelable
import com.example.redfruit.data.model.interfaces.IoEmbed
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
data class YoutubeoEmbed(
    override val provider_url: String = "",
    override val type: String = "video",
    val thumbnail_url: String = "",
    val thumbnail_width: Int = 0,
    val thumbnail_height: Int = 0,
    override val html: String = "",
    override val width: Int = 0,
    override val height: Int = 0
) : IoEmbed, Parcelable {
    val youtubeId: String get() = html.substringAfter("embed/").substringBefore("?feature")
}
