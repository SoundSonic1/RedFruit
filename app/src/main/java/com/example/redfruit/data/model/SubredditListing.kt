package com.example.redfruit.data.model

import com.example.redfruit.data.model.Interfaces.Listing

data class SubredditListing(val name: String,
                            override var before: String,
                            override var after: String,
                            override var children: List<Post>
) : Listing<Post>