package com.example.redfruit.data.repositories

import com.example.redfruit.data.model.Post
import com.example.redfruit.data.model.enumeration.SortBy

interface IPostsRepository {
    suspend fun getData(sub: String, sortBy: SortBy, limit: Int): List<Post>
    fun clearData()
}