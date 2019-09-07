package com.example.redfruit.data.api

import com.example.redfruit.data.model.Images.ImageSource
import com.example.redfruit.data.model.Images.RedditImage
import com.example.redfruit.data.model.Post
import com.example.redfruit.data.model.Preview
import com.google.gson.*
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.net.URL

/**
 * Implements the Repository pattern
 * For now it is a Singleton. Will change once we cache data
 */
class PostRepository : IRepository<List<Post>> {

    private val _data = mutableListOf<Post>()
    private var after = "null"

    override fun getData(url: String): List<Post> {
        var redditUrl = url
        if (after != "null") {
            redditUrl = "$redditUrl&after=$after"
        }
        val response = URL(redditUrl).readText()
        val jsonObjResponse = JsonParser().parse(response).asJsonObject
        val jsonData = jsonObjResponse.getAsJsonObject("data")
        // bookmark index of last post
        after = jsonData.get("after").asString
        // JSONArray of children aka posts
        val jsonChildren = jsonData.getAsJsonArray("children")
        val gson = GsonBuilder()
            .registerTypeAdapter(Post::class.java, PostDeserializer())
            .setPrettyPrinting()
            .create()

        _data.addAll(gson.fromJson(jsonChildren, object: TypeToken<List<Post>>() {}.type))

        return _data.toList()
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
                id = jsonData.get("id").asString,
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

    companion object {
        // see: https://www.reddit.com/r/redditdev/comments/9ncg2r/changes_in_api_pictures/
        private fun removeEncoding(url: String) = url.replace("amp;s", "s")
    }
}