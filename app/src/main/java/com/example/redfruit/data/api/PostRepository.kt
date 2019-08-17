package com.example.redfruit.data.api

import com.example.redfruit.data.model.Images.ImageSource
import com.example.redfruit.data.model.Images.RedditImage
import com.example.redfruit.data.model.Post
import com.example.redfruit.data.model.Preview
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
object PostRepository : IRepository<Collection<Post>> {

    override fun getData(url: String): Collection<Post> {
        val response = URL(url).readText()
        // JSONArray of children aka posts
        val jsonChildren = JSONObject(response).getJSONObject("data").getJSONArray("children").toString()
        val gson = GsonBuilder()
            .registerTypeAdapter(Post::class.java, PostDeserializer())
            .setPrettyPrinting()
            .create()

        return gson.fromJson(
            jsonChildren,
            object: TypeToken<Collection<Post>>() {}.type)
    }

    private class PostDeserializer : JsonDeserializer<Post> {
        /**
         * Used to deserialize a json array
         */
        override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): Post {
            // Throw NPE here if there is no JsonObject
            val jsonData = json!!.asJsonObject.getAsJsonObject("data")
            val jsonPreview = jsonData.getAsJsonObject("preview")
            val enabled = jsonPreview?.get("enabled")?.asBoolean ?: false
            val jsonArrayImages = jsonPreview?.getAsJsonArray("images")

            var images: List<RedditImage>? = null
            if (enabled) {
                val gson = GsonBuilder()
                    .registerTypeAdapter(ImageSource::class.java, ImageSourceDeserializer())
                    .setPrettyPrinting()
                    .create()
                images = jsonArrayImages?.map() { item ->
                    RedditImage(
                        source = gson.fromJson(item.asJsonObject.get("source"), object : TypeToken<ImageSource>() {}.type),
                        resolutions = gson.fromJson(item.asJsonObject.get("resolutions"), object : TypeToken<List<ImageSource>>() {}.type)
                    )
                }
            }


            return Post(
                title = jsonData.get("title").asString,
                author = jsonData.get("author")?.asString ?: "Unknown",
                ups = jsonData.get("ups").asInt,
                downs = jsonData.get("downs").asInt,
                preview = Preview(
                    enabled = enabled,
                    images = images ?: listOf()
                ),
                over_18 = jsonData.get("over_18")?.asBoolean ?: false,
                stickied = jsonData.get("stickied")?.asBoolean ?: false,
                url = jsonData.get("url")?.asString ?: ""
            )
        }

    }

    private class ImageSourceDeserializer : JsonDeserializer<ImageSource> {
        override fun deserialize(
            json: JsonElement?,
            typeOfT: Type?,
            context: JsonDeserializationContext?
        ): ImageSource {
            val jsonObj = json!!.asJsonObject
            val url = removeEncoding(jsonObj.get("url")?.asString ?: "")
            val width = jsonObj.get("width").asInt
            val height = jsonObj.get("height").asInt

            return ImageSource(
                url = url,
                width = width,
                height = height
            )
        }
    }

    // see: https://www.reddit.com/r/redditdev/comments/9ncg2r/changes_in_api_pictures/
    private fun removeEncoding(url: String) = url.replace("amp;s", "s")
}