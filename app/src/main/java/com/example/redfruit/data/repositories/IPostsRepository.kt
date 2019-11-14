package com.example.redfruit.data.repositories

import com.example.redfruit.data.model.Post
import com.example.redfruit.data.model.enumeration.SortPostBy

interface IPostsRepository {
    suspend fun getPosts(sub: String, sortPostBy: SortPostBy, limit: Int): List<Post>
    fun clearPosts()
}
