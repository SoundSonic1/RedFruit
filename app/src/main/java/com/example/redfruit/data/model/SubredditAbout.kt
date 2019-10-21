package com.example.redfruit.data.model

import com.beust.klaxon.Json

/**
 * Contains information to populate the toolbar and the home fragments
 */
data class SubredditAbout(
    val display_name: String,
    @Json(name = "over_18")
    private val _over18: Boolean? = false,
    @Json(name = "subscribers")
    private val _subscribers: Long? = 0,
    @Json(name = "public_description")
    private val _publicDescription: String? = "",
    @Json(name = "description")
    private val _description: String? = "",
    @Json(name = "icon_img")
    private val _iconImg: String? = "",
    @Json(name = "community_icon")
    private val _communityIcon: String? = "",
    @Json("header_img")
    private val _headerImg: String? = "",
    @Json("banner_background_image")
    private val _bannerBackgroundImage: String? = "",
    @Json("subreddit_type")
    private val _subreddit_type: String? = ""
) {
    val over18 get() = _over18 ?: false
    val subscribers get() = _subscribers ?: 0
    val publicDescription get() = _publicDescription ?: ""
    val description get() = _description ?: ""
    val iconImg get() = _iconImg ?: ""
    val communityIcon get() = _communityIcon ?: ""
    val headerImg get() = _headerImg ?: ""
    val bannerBackgroundImage get() = _bannerBackgroundImage ?: ""
    val subredditType = _subreddit_type ?: "private"
}