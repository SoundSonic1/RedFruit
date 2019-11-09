package com.example.redfruit.data.api

import com.example.redfruit.data.model.Token
import com.example.redfruit.util.Constants
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Url

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
