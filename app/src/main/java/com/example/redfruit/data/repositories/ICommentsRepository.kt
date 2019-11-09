package com.example.redfruit.data.repositories

import com.example.redfruit.data.model.Comment

interface ICommentsRepository {
    suspend fun getComments(limit: Int): List<Comment>
}
