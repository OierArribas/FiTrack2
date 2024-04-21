package com.example.fittrack.login

import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface Api {
    @GET("getuser")
    suspend fun getUser(

        @Query("username") name: String,
        @Query("password") hashed_password: String,
    ): FiTrackUser

    @POST("adduser")
    suspend fun adduser(
        @Body userData: UserData
    ): FiTrackUser

    @PUT("uploadPhoto")
    suspend fun uploadPhoto(
        @Query("username") username: String,
        @Query("photo_bitmap_string") photoBitmapString: String
    ): FiTrackUser

    @GET("subscribeTopic")
    suspend fun subscribeTopic(
        @Query("token") token: String,

    ): FiTrackUser


    companion object {
        const val BASE_URL = "http://138.68.136.147:8000/"
        //const val BASE_URL = "http://172.24.0.1:8000/"
    }
}