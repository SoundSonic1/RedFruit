package com.example.redfruit.data.model

import com.example.redfruit.data.model.interfaces.ISource
import com.example.redfruit.data.model.interfaces.IVotable

/** Contains the necessary information of a reddit post
 *
 * @property id unique
 * @property title of the post
 * @property author of the post
 * @property ups amount of upvotes
 * @property downs amount of downvotes
 * @property preview contains sources like images
 * @property over_18 marked nsfw post
 * @property stickied post
 * @property url of the post
 */
data class Post(
    val id: String,
    val title: String,
    val author: String,
    override val ups: Int,
    override val downs: Int,
    val score: Int,
    val num_comments: Int,
    val preview: Preview,
    val over_18: Boolean,
    val stickied: Boolean,
    override val url: String
) : ISource, IVotable