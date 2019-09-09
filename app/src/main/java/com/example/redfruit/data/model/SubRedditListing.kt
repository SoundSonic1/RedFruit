package com.example.redfruit.data.model

import com.example.redfruit.data.model.Interfaces.IListing

data class SubRedditListing(val name: String,
                            override var before: String = "",
                            override var after: String = "",
                            override val children: MutableList<Post> = mutableListOf()
) : IListing<Post>