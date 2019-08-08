package com.example.redfruit.data.api

import com.example.redfruit.data.model.Post
import com.google.gson.*
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.net.URL


class OpenApi : IApi{

    override fun getPosts(): List<Post> {
        val response = URL(url).readText()
        val gson = GsonBuilder()
            .registerTypeAdapter(Post::class.java, Deserialzer())
            .setPrettyPrinting()
            .create()
        val postList: List<Post> = gson.fromJson(
            response,
            object: TypeToken<List<Post>>() {}.type)
        return postList
    }

    private class Deserialzer : JsonDeserializer<Post> {
        override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): Post {
            // json is one specification of a mensa, as jsonObject
            // iterate through the key-value pairs
            // json needs to be non-null, else throw NPE
            val jsonObject: JsonObject = json!!.asJsonObject
            // json node needs to have keys id and name, else throw NPE
            val name: String = jsonObject.get("name")!!.asString

            return Post(
                title = name
            )
        }
    }

    companion object {
        private const val url = "https://openmensa.org/api/v2/canteens?ids=78,79,80,81,82,83"
    }
}