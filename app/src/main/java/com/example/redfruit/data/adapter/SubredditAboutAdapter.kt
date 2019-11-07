package com.example.redfruit.data.adapter

import com.example.redfruit.data.model.SubredditAbout
import com.squareup.moshi.FromJson
import com.squareup.moshi.Moshi
import javax.inject.Inject

class SubredditAboutAdapter @Inject constructor() {

    private val moshi = Moshi.Builder().build()
    private val mapAdapter = moshi.adapter(Map::class.java)
    private val subredditAdapter = moshi.adapter(SubredditAbout::class.java)

    @FromJson
    fun fromJson(jsonMap: Map<*, *>): SubredditAbout? {

        if (jsonMap["kind"] != "t5") return null

        val data = jsonMap["data"] as? Map<*, *> ?: return null
        val dataString = mapAdapter.toJson(data)

        return subredditAdapter.fromJson(dataString)
    }
}