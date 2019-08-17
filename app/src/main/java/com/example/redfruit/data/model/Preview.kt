package com.example.redfruit.data.model

import com.example.redfruit.data.model.Images.RedditImage

data class Preview(val enabled: Boolean, val images: List<RedditImage>) {
    fun getFirstImage() = images.firstOrNull()
}
