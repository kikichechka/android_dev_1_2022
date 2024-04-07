package com.example.photosights.data

import android.location.Location
import com.example.photosights.data.model.Photo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RepoDatabase @Inject constructor(private val photoDao: PhotoDao) {

    fun getAllData(): Flow<List<Photo>> {
        return photoDao.getAll()
    }

    suspend fun getLastPhoto(uri: String): Photo {
        return photoDao.getLast(uri)
    }

    suspend fun deleteData(uri: String) {
        photoDao.delete(uri)
    }

    suspend fun insertData(uri: String, location: Location) {
        photoDao.insert(Photo(uri, location))
    }
}
