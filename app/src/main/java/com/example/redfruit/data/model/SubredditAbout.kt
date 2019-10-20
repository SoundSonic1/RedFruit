package com.example.redfruit.data.model

import com.beust.klaxon.Json

/**
 * Contains information to populate the toolbar and the home fragments
 */
data class SubredditAbout(
    val display_name: String,
    val description: String = "",
    val public_description: String = "",
    val subscribers: Long = 0,
    val over_18: Boolean = false,
    @Json(name = "icon_img")
    private val _icon_img: String? = "",
    @Json(name = "community_icon")
    private val _community_icon: String? = "",
    @Json("header_img")
    private val _header_img: String? = "",
    @Json("banner_background_image")
    private val _banner_background_image: String? = ""
) {
    val icon_img get() = _icon_img ?: ""
    val community_icon get() = _community_icon ?: ""
    val header_img get() = _header_img ?: ""
    val banner_background_image get() = _banner_background_image ?: ""
}