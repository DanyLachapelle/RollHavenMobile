package com.school.rollhaven_mobile.repositories

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

data class UserLogin(
    val pseudo: String,
    val password: String
)
data class UserRegister(
    val pseudo: String,
    val email: String,
    val password: String
)
data class LoginResponse(val token: String)
data class RegisterResponse(
    val success: Boolean,
    val message: String,
    val token: String? = null
)
interface IRollHavenRepository {
    @POST("api/users/login")
    fun login(@Body user: UserLogin): Call<LoginResponse>

    @POST("/api/users")
    fun register(@Body user: UserRegister): Call<Void>
}