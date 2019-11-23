package com.example.redfruit.data.model

import android.os.Parcelable
import com.example.redfruit.data.model.interfaces.ICreated
import com.example.redfruit.data.model.interfaces.ISource
import com.example.redfruit.data.model.media.SecureMedia
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

/**
 * Class which contains detailed information about subreddit post
 *
 * @property id of post
 * @property title of the post
 * @property author of the post
 * @property score total score after calculating ups and downs (might fluctuate)
 * @property num_comments total number of comments
 * @property preview contains sources like images
 * @property secureMedia contains video details
 * @property over_18 marked nsfw post
 * @property stickied post
 * @property selftext post information details
 * @property url of the post
 */
@JsonClass(generateAdapter = true)
@Parcelize
data class Post(
    val id: String,
    val title: String = "Unknown",
    val author: String = "Unknown",
    val score: String = "0",
    val num_comments: String = "0",
    val post_hint: String = "",
    val preview: Preview,
    val secureMedia: SecureMedia? = null,
    val gildings: Gildings,
    val over_18: Boolean = false,
    val stickied: Boolean = false,
    val selftext: String = "",
    val subreddit: String,
    override val created: Long = 0,
    override val created_utc: Long = 0,
    override val url: String = ""
) : ISource, ICreated, Parcelable
