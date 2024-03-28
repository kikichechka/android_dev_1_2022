package com.example.seventeenthhw.data

import javax.inject.Inject

class Repository @Inject constructor() {
    suspend fun loadMars(date: String): ListMars {
        return MyRetrofit.searchMoon.getListMars(date)
    }

    suspend fun loadAstronomy(date: String): List<AstronomyPicture> {
        return MyRetrofit.searchMoon.getListAstronomyPicture(date)
    }
}
