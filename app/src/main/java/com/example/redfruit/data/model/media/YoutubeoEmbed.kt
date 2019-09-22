package com.example.redfruit.data.model.media

import com.example.redfruit.data.model.interfaces.IoEmbed

data class YoutubeoEmbed(
    override val provider_url: String = "",
    override val type: String = "video",
    val thumbnail_url: String = "",
    val thumbnail_width: Int = 0,
    val thumbnail_height: Int = 0,
    override val html: String = "",
    override val width: Int = 0,
    override val height: Int = 0
) : IoEmbed {
    val youtubeId: String get() = html.substringAfter("embed/").substringBefore("?feature")
}
