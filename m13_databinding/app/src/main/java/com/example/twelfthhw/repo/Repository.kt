package com.example.twelfthhw.repo

import kotlinx.coroutines.delay

class Repository {
    suspend fun searchString(str: String): String? {
        delay(2000)
        return loadFromLocal(str)
    }

    private fun loadFromLocal(str: String): String? {
        return null
    }
}
