package com.example.redfruit.data.model

import com.example.redfruit.data.model.Interfaces.Listing

data class SubredditListing(var name: String,
                            override var before: String,
                            override var after: String,
                            override var children: MutableList<Post>
) : Listing<Post>