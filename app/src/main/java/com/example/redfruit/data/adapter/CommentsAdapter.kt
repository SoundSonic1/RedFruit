package com.example.redfruit.data.adapter

import com.beust.klaxon.JsonObject
import com.beust.klaxon.Parser
import com.beust.klaxon.jackson.jackson
import com.example.redfruit.data.model.Comment
import com.example.redfruit.data.model.Gildings
import com.squareup.moshi.FromJson
import com.squareup.moshi.Moshi

class CommentsAdapter {
    private val moshi = Moshi.Builder().build()
    private val mapAdapter = moshi.adapter(Map::class.java)
    private val gildingsAdapter = moshi.adapter(Gildings::class.java)
    private val parser = Parser.jackson()


    @FromJson
    fun fromJson(jsonListMap: List<Map<*, *>>): List<Comment> {

        val jsonObjList = jsonListMap.map {
            parser.parse(StringBuilder(mapAdapter.toJson(it))) as JsonObject
        }.filter {
            it.string("kind") == "Listing" && it.obj("data")?.array<JsonObject>("children")?.any {
                it.string("kind") == "t1"
            } ?: false
        }

        return jsonObjList.flatMap {
            fromJsonDetail(it)
        }
    }

    private fun fromJsonDetail(jsonObj: JsonObject): List<Comment> {

        val data = jsonObj.obj("data") ?: return listOf()

        val array = data.array<JsonObject>("children") ?: return listOf()

        return array.filter {
            it.string("kind") == "t1"
        }.map {
            deserializeDetail(it)
        }
    }

    private fun deserializeDetail(json: JsonObject): Comment {
        val data = json.obj("data")!!

        // replies can be json obj or empty string
        val replies = if (data["replies"] is JsonObject) {
            data.obj("replies")
        } else {
            null
        }

        return Comment(
            author = data.string("author") ?: "Unknown",
            body = data.string("body") ?: "",
            created = data.long("created") ?: 0,
            created_utc = data.long("created_utc") ?: 0,
            gildings = gildingsAdapter.fromJson(data.obj("gildings")!!.toJsonString())!!,
            id = data.string("id") ?: "",
            score = data.int("score") ?: 0,
            replies = if (replies != null) {
                fromJsonDetail(replies)
            } else {
                listOf()
            },
            depth = data.int("depth") ?: 0
        )
    }
}