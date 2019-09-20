package com.example.redfruit.data.model

import com.example.redfruit.data.model.interfaces.ISource
import com.example.redfruit.data.model.media.SecureMedia

/** Contains the necessary information of a reddit post
 *
 * @property id unique
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
data class Post(
    val id: String,
    val title: String,
    val author: String,
    val score: String,
    val num_comments: String,
    val preview: Preview,
    val secureMedia: SecureMedia?,
    val gildings: Gildings,
    val over_18: Boolean,
    val stickied: Boolean,
    val selftext: String,
    override val url: String
) : ISource
