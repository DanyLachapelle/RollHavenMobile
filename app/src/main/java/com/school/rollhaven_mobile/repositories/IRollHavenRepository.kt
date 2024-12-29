package com.school.rollhaven_mobile.repositories

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

data class UserLogin(
    val pseudo: String,
    val password: String
)
data class UserRegister(
    val pseudo: String,
    val email: String,
    val password: String
)
data class LoginResponse(val token: String,val id: Int?=null)
data class RegisterResponse(
    val success: Boolean,
    val message: String,
    val token: String? = null,
    val userId: Int
)

data class Campaign(
    val id: String,
    val name: String,
    val description: String
)
interface IRollHavenRepository {
    @POST("api/users/login")
    fun login(@Body user: UserLogin): Call<LoginResponse>

    @POST("/api/users")
    fun register(@Body user: UserRegister): Call<Void>

    @GET("users/{userId}/campaigns")
    fun getCampaigns(
        @Path("userId") userId: Int
    ): Call<List<Campaign>>

    @GET("campaigns/Public")
    suspend fun getPublicCampaigns(): List<Campaign>
}