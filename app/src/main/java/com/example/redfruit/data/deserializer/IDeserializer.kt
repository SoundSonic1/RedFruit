package com.example.redfruit.data.deserializer

import com.beust.klaxon.JsonObject

/**
 * Interface for custom deserializer with JsonObject
 */
interface IDeserializer<T> {
    fun deserialize(json: JsonObject): T
}