package com.example.seventeenthhw.data

import com.example.seventeenthhw.API_KEY_NASA
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Query

private const val BASE_URL = "https://api.nasa.gov"

object MyRetrofit {
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val searchMoon: SearchMoon = retrofit.create(SearchMoon::class.java)
}

interface SearchMoon {
    @GET("/mars-photos/api/v1/rovers/curiosity/photos?max_date=")
    suspend fun getListMars(
        @Query("earth_date") earthDate: String,
        @Query("api_key") apiKey: String = API_KEY_NASA
    ): ListMars

    @GET("/planetary/apod")
    suspend fun getListAstronomyPicture(
        @Query("start_date") earthDate: String,
        @Query("api_key") apiKey: String = API_KEY_NASA
    ): List<AstronomyPicture>
}
