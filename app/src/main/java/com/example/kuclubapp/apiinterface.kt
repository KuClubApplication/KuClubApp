package com.example.myauth2

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

data class LoginRequest(val userId: String, val password: String)
data class TokenResponse(val token: String)
data class VerifyTokenRequest(val token: String)
data class VerifyTokenResponse(val message: String)
data class IdTokenRequest(val idToken: String)
data class ServerResponse(val success: Boolean, val message: String)

interface ApiService {
    @POST("/login")
    fun login(@Body request: LoginRequest): Call<TokenResponse>

    @POST("/verifyToken")
    fun verifyToken(@Body request: VerifyTokenRequest): Call<VerifyTokenResponse>

    @POST("/sendIdToken")
    fun sendIdToken(@Body request: IdTokenRequest): Call<ServerResponse>
}
