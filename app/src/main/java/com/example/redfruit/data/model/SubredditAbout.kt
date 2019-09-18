package com.example.redfruit.data.model

/**
 * Contains information to populate the toolbar and the home fragments
 */
data class SubredditAbout(
    val display_name: String,
    val description: String = "",
    val public_description: String = "",
    val subscribers: Int = 0,
    val icon_img: String = "",
    val community_icon: String = "",
    val header_img: String = "",
    val banner_background_image: String = "",
    val over_18: Boolean = false
)