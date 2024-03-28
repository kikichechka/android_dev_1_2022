package com.example.sixteenthhw.data

import javax.inject.Inject

class UsefulActivitiesRepository @Inject constructor(){
    suspend fun getUsefulActivity() : UsefulActivityDto {
        return RetrofitServices.searchApi.getActivity()
    }
}
