package com.example.redfruit.data.model.Images

import com.example.redfruit.data.model.Interfaces.ISource

data class ImageSource(override val url: String,
                       val width: Int,
                       val height: Int
) : ISource