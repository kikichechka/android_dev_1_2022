package com.example.fourteenth.ui

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://randomuser.me"

object RetrofitServices {
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val searchUserApi: SearchUserApi = retrofit.create(SearchUserApi::class.java)
}

interface SearchUserApi {
    @GET("/api/")
    suspend fun getUserInfo() : Response<UsersModels>
}
