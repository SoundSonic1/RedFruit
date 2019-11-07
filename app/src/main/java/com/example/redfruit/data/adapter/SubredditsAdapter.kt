package com.example.redfruit.data.adapter

import com.beust.klaxon.JsonObject
import com.beust.klaxon.Parser
import com.beust.klaxon.jackson.jackson
import com.example.redfruit.data.model.SubredditAbout
import com.squareup.moshi.FromJson
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types

class SubredditsAdapter {

    private val moshi = Moshi.Builder().add(SubredditAboutAdapter()).build()

    private val type = Types.newParameterizedType(List::class.java, SubredditAbout::class.java)

    private val mapAdapter = moshi.adapter(Map::class.java)

    private val subredditAdapter = moshi.adapter<List<SubredditAbout>>(type)

    @FromJson
    fun fromJson(jsonMap: Map<*, *>): List<SubredditAbout> {

        val jsonString = mapAdapter.toJson(jsonMap)
        val jsonStringBuilder = StringBuilder(jsonString)
        val jsonObj = Parser.jackson().parse(jsonStringBuilder) as JsonObject

        if (jsonObj.string("kind") != "Listing") return listOf()

        val data = jsonObj.obj("data") ?: return listOf()
        val children = data.array<JsonObject>("children") ?: return listOf()

        return subredditAdapter.fromJson(children.toJsonString())!!
    }
}