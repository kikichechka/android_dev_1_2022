package com.example.sixteenthhw.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://www.boredapi.com"
object RetrofitServices {
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val searchApi: SearchApi = retrofit.create(SearchApi::class.java)
}

interface SearchApi {
    @GET("/api/activity/")
    suspend fun getActivity(): UsefulActivityDto
}
