package com.example.redfruit.data.adapter

import com.example.redfruit.data.model.Comment
import com.example.redfruit.data.model.Gildings
import com.squareup.moshi.FromJson
import com.squareup.moshi.Moshi
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
class CommentsAdapter @Inject constructor() {
    private val moshi = Moshi.Builder().build()
    private val mapAdapter = moshi.adapter(Map::class.java)
    private val gildingsAdapter = moshi.adapter(Gildings::class.java)

    @FromJson
    fun fromJson(jsonListMap: List<Map<*, *>>): List<Comment> = jsonListMap.flatMap {
        fromJsonDetail(it)
    }

    private fun fromJsonDetail(jsonMap: Map<*, *>): List<Comment> {

        val data = jsonMap["data"] as? Map<*, *> ?: return listOf()

        val children = data["children"] as? List<Map<*, *>> ?: return listOf()

        // remove post from children
        return children.filter {
            it["kind"] == "t1"
        }.map {
            deserializeDetail(it)
        }
    }

    private fun deserializeDetail(json: Map<*, *>): Comment {
        val data = json["data"] as Map<*, *>

        // replies can be json obj or empty string
        val replies = data["replies"] as? Map<*, *>

        return Comment(
            author = data["author"] as? String ?: "Unknown",
            body = data["body"] as? String ?: "",
            created = (data["created"] as? Double)?.toLong() ?: 0,
            created_utc = (data["created_utc"] as? Double)?.toLong() ?: 0,
            gildings = gildingsAdapter.fromJson(mapAdapter.toJson(data["gildings"] as Map<*, *>)) ?: Gildings(),
            id = data["id"] as? String ?: "",
            score = (data["score"] as? Double)?.toInt() ?: 0,
            replies = if (replies != null) {
                fromJsonDetail(replies)
            } else {
                listOf()
            },
            depth = (data["depth"] as? Double)?.toInt() ?: 0
        )
    }
}
