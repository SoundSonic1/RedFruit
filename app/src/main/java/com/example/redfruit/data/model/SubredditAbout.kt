package com.example.redfruit.data.model

import com.squareup.moshi.JsonClass

/**
 * Contains information to populate the toolbar and the home fragments
 */
@JsonClass(generateAdapter = true)
data class SubredditAbout(
    val display_name: String,
    val over18: Boolean = false,
    val subscribers: Long? = 0,
    val public_description: String? = "",
    val description: String? = "",
    val icon_img: String? = "",
    val community_icon: String? = "",
    val header_img: String? = "",
    val banner_background_image: String? = "",
    val subreddit_type: String? = ""
)