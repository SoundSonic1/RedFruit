package com.example.redfruit.data.adapter

import com.example.redfruit.data.model.SubredditAbout
import com.squareup.moshi.FromJson
import com.squareup.moshi.Moshi

class SubredditsAdapter {

    private val moshi = Moshi.Builder().add(SubredditAboutAdapter()).build()

    private val mapAdapter = moshi.adapter(Map::class.java)

    private val subredditAdapter = moshi.adapter(SubredditAbout::class.java)

    @FromJson
    fun fromJson(jsonMap: Map<*, *>): List<SubredditAbout> {

        if (jsonMap["kind"] != "Listing") return listOf()

        val data = jsonMap["data"] as? Map<*, *> ?: return listOf()

        @Suppress("UNCHECKED_CAST")
        val children = data["children"] as? List<Map<*, *>> ?: listOf()

        return children.map {
            subredditAdapter.fromJson(mapAdapter.toJson(it))!!
        }
    }
}