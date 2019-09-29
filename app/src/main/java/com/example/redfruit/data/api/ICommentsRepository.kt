package com.example.redfruit.data.api

import com.example.redfruit.data.model.Comment

interface ICommentsRepository {
    fun getComments(limit: Int): List<Comment>
}