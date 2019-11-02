package com.example.redfruit.data.deserializer

import com.beust.klaxon.JsonObject
import com.beust.klaxon.Klaxon
import com.example.redfruit.data.model.Comment
import com.example.redfruit.data.model.Gildings
import com.example.redfruit.util.IFactory

/**
 * Custom deserializer for json comments
 */
class CommentDeserializer(
    private val klaxonFactory: IFactory<Klaxon>
) : IDeserializer<List<Comment>> {

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
            gildings = klaxonFactory.build().parseFromJsonObject<Gildings>(data.obj("gildings")!!)!!,
            id = data.string("id") ?: "",
            score = data.int("score") ?: 0,
            replies = if (replies != null) {
                deserialize(replies)
            } else {
                listOf()
            },
            depth = data.int("depth") ?: 0
        )
    }

    /**
     * Parses the json obj and returns a list of comments
     */
    override fun deserialize(json: JsonObject): List<Comment> {
        val data = json.obj("data") ?: return listOf()

        val array = data.array<JsonObject>("children") ?: return listOf()

        return array.filter {
            it.string("kind") == "t1"
        }.map {
            deserializeDetail(it)
        }
    }
}