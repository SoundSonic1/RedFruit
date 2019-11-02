package com.example.redfruit.data.deserializer

import com.beust.klaxon.JsonObject
import com.beust.klaxon.Klaxon
import com.example.redfruit.data.model.SubredditAbout
import com.example.redfruit.util.IFactory

class SubredditsDeserializer(
    private val klaxonFactory: IFactory<Klaxon>
) : IDeserializer<List<SubredditAbout>> {

    override fun deserialize(json: JsonObject): List<SubredditAbout> {

        val klaxon = klaxonFactory.build()

        if (json.string("kind") != "Listing") return listOf()

        val data = json.obj("data") ?: return listOf()

        val children = data.array<JsonObject>("children") ?: return listOf()

        return children.map {
            klaxon.parseFromJsonObject<SubredditAbout>(it.obj("data")!!)!!
        }
    }
}