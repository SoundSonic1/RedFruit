package com.example.redfruit.data.api

import com.example.redfruit.data.model.Token
import com.example.redfruit.util.Constants
import retrofit2.http.*

interface OAuthApi {

    // TODO: make call synchronous
    @FormUrlEncoded
    @Headers("User-Agent: ${Constants.USER_AGENT}")
    @POST
    suspend fun refreshAppOnlyToken(
        @Url url: String,
        @Header("Authorization") credentials: String,
        @Field("device_id") deviceId: String,
        @Field("grant_type") grantType: String = Constants.GRANT_TYPE
    ): Token
}