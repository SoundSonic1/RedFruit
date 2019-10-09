package com.example.redfruit.data.api

import com.example.redfruit.data.model.Token

interface ITokenProvider {
    val token: Token?
    fun refreshToken(): Token?
}