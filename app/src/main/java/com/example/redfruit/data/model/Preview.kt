package com.example.redfruit.data.model

import android.os.Parcelable
import com.example.redfruit.data.model.images.RedditImage
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

/**
 * Post preview
 *
 * @property enabled whether the preview is enabled by default
 * @property images Preview can contain images
 */
@JsonClass(generateAdapter = true)
@Parcelize
data class Preview(
    val enabled: Boolean = false,
    val images: List<RedditImage> = listOf()
) : Parcelable {

    val firstImage get() = images.firstOrNull()
}
