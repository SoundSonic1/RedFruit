package com.example.redfruit.data.model.media

import com.example.redfruit.data.model.interfaces.IoEmbed

data class Oembed(
    override val type: String,
    override val version: String,
    override val html: String,
    override val width: Int,
    override val height: Int
) : IoEmbed
