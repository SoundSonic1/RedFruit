package com.example.redfruit.data.api

import android.util.Log
import com.example.redfruit.data.model.Token
import com.example.redfruit.util.Constants
import java.util.Base64
import javax.inject.Inject
import javax.inject.Named

/**
 * Manages the access token which is required for api calls
 */
@JvmSuppressWildcards
class TokenProvider @Inject constructor(
    @Named("ClientId") clientId: String,
    @Named("DeviceId") private val deviceId: String,
    private val oAuthApi: OAuthApi,
    private var _token: Token? = null,
    private val tokenRefreshListener: (Token?) -> Unit = {}
) : ITokenProvider {

    private val clientIdPw = "$clientId:"

    private val encodeClientID = String(Base64.getEncoder().encode(clientIdPw.toByteArray()))

    private val basic = "Basic $encodeClientID"

    override val token get() = _token

    override suspend fun refreshToken(): Token? {

        Log.d("TokenAuthenticator", "requesting new token")

        _token = try {
            oAuthApi.refreshAppOnlyToken(
                Constants.ACCESS_TOKEN_URL,
                basic,
                deviceId
            )
        } catch (e: Throwable) {
            null
        }

        tokenRefreshListener(token)

        return token
    }
}
