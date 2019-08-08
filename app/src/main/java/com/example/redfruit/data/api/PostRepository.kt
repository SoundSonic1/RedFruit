package com.example.redfruit.data.api

import com.example.redfruit.data.model.Post
import com.google.gson.*
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.net.URL

/**
 * Implements the Repository pattern
 */
object PostRepository : IRepository<Post> {

    override fun getData(): Collection<Post> {
        val response = URL("https://openmensa.org/api/v2/canteens?ids=78,79,80,81,82,83").readText()
        val gson = GsonBuilder()
            .registerTypeAdapter(Post::class.java, Deserialzer())
            .setPrettyPrinting()
            .create()

        return gson.fromJson(
            response,
            object: TypeToken<Collection<Post>>() {}.type)
    }

    private class Deserialzer : JsonDeserializer<Post> {
        override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): Post {
            val jsonObject: JsonObject = json!!.asJsonObject
            // json node needs to have keys id and name, else throw NPE
            val name: String = jsonObject.get("name")!!.asString

            return Post(
                title = name
            )
        }
    }
}