package com.example.redfruit.data.api

import com.example.redfruit.data.model.Post
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.reflect.TypeToken
import org.json.JSONObject
import java.lang.reflect.Type
import java.net.URL

/**
 * Implements the Repository pattern
 * For now it is a Singleton. Will change once we cache data
 */
object PostRepository : IRepository<Post> {

    override fun getData(url: String): Collection<Post> {
        val response = URL(url).readText()
        // JSONArray of children aka posts
        val jsonChildren = JSONObject(response).getJSONObject("data").getJSONArray("children").toString()
        val gson = GsonBuilder()
            .registerTypeAdapter(Post::class.java, SubRedditDeserializer())
            .setPrettyPrinting()
            .create()

        return gson.fromJson(
            jsonChildren,
            object: TypeToken<Collection<Post>>() {}.type)
    }

    private class SubRedditDeserializer : JsonDeserializer<Post> {
        /**
         * Used to deserialize a json array
         */
        override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): Post {
            // Throw NPE here if there is no JsonObject
            val jsonData = json!!.asJsonObject.getAsJsonObject("data")
            val jsonPreviewImage = jsonData.getAsJsonObject("preview")
            val jsonImages = jsonPreviewImage?.getAsJsonArray("images")
            val jsonImageSource = jsonImages?.get(0)?.asJsonObject
            val imageUrlAmp = jsonImageSource?.getAsJsonObject("source")?.get("url")?.asString ?: ""
            // Remove spurious encoding
            // see: https://www.reddit.com/r/redditdev/comments/9ncg2r/changes_in_api_pictures/
            val imageUrl = imageUrlAmp.replace("amp;s", "s")

            return Post(
                title = jsonData.get("title").asString,
                imageUrl = imageUrl
            )
        }
    }
}