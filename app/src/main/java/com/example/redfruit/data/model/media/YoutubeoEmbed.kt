package com.example.redfruit.data.model.media

import com.example.redfruit.data.model.interfaces.IoEmbed

data class YoutubeoEmbed(
    override val provider_url: String?,
    override val type: String,
    override val html: String,
    override val width: Int,
    override val height: Int
) : IoEmbed {
    val youtubeId: String get() = html.substringAfter("embed/").substringBefore("?feature")
}
