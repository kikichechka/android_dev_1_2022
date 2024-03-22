package com.example.fourteenth.repo

import com.example.fourteenth.ui.RetrofitServices
import com.example.fourteenth.ui.UsersModels
import kotlinx.coroutines.delay

class UserRepository {

    suspend fun loadUser(): UsersModels? {
        delay(3000)
        return retrofit()
    }

    private suspend fun retrofit(): UsersModels? {
        return RetrofitServices.searchUserApi.getUserInfo().body()
    }
}
