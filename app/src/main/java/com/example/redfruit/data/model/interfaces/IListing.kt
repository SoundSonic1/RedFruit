package com.example.redfruit.data.model.interfaces

/**
 * see https://github.com/reddit-archive/reddit/wiki/json
 */
interface IListing<T> {
    var before: String
    var after: String
    val children: MutableList<T>
}
