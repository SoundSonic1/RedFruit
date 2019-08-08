package com.example.redfruit.data.api

import com.example.redfruit.data.model.Post

interface IApi {
    fun getPosts(): List<Post>
}