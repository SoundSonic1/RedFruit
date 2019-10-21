package com.example.redfruit.data.deserializer

import com.beust.klaxon.JsonObject
import com.beust.klaxon.Klaxon
import com.example.redfruit.data.model.SubredditAbout

class SubredditsDeserializer(
    private val klaxon: Klaxon
) : IDeserializer<List<SubredditAbout>> {

    override fun deserialize(json: JsonObject): List<SubredditAbout> {

        if (json.string("kind") != "Listing") return listOf()

        val data = json.obj("data") ?: return listOf()

        val children = data.array<JsonObject>("children") ?: return listOf()

        return children.map {
            klaxon.parse<SubredditAbout>(it.obj("data")!!.toJsonString())!!
        }
    }
}