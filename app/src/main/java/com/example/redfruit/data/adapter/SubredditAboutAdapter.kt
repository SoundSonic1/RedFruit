package com.example.redfruit.data.adapter

import com.beust.klaxon.JsonObject
import com.beust.klaxon.Parser
import com.beust.klaxon.jackson.jackson
import com.example.redfruit.data.model.SubredditAbout
import com.squareup.moshi.FromJson
import com.squareup.moshi.Moshi

class SubredditAboutAdapter {

    private val moshi = Moshi.Builder().build()
    private val mapAdapter = moshi.adapter(Map::class.java)
    private val subredditAdapter = moshi.adapter(SubredditAbout::class.java)

    @FromJson
    fun fromJson(jsonMap: Map<*, *>): SubredditAbout? {

        val jsonString = mapAdapter.toJson(jsonMap)
        val jsonStringBuilder = StringBuilder(jsonString)
        val jsonObj = Parser.jackson().parse(jsonStringBuilder) as JsonObject

        if (jsonObj.string("kind") != "t5") return null

        val data = jsonObj.obj("data") ?: return null

        return subredditAdapter.fromJson(data.toJsonString())
    }
}