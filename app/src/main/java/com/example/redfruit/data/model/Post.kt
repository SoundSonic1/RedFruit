package com.example.redfruit.data.model

import com.example.redfruit.data.model.Interfaces.IVotable

data class Post(
    val title: String,
    val author: String,
    override val ups: Int,
    override val downs: Int,
    val preview: Preview,
    val over_18: Boolean,
    val stickied: Boolean,
    val url: String
) : IVotable