package com.example.redfruit.data.api

import com.example.redfruit.data.model.Comment

interface ICommentsRepository {
    suspend fun getComments(limit: Int): List<Comment>
}