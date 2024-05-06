package com.example.seventeenthhw.data

import com.example.seventeenthhw.data.model.AstronomyPicture
import javax.inject.Inject

class Repository @Inject constructor() {
    suspend fun loadMars(date: String): ListMars {
        return MyRetrofit.searchMars.getListMars(date)
    }

    suspend fun loadAstronomy(minDate: String, maxDate: String): List<AstronomyPicture> {
        return MyRetrofit.searchMars.getListAstronomyPicture(minDate, maxDate)
    }
}
