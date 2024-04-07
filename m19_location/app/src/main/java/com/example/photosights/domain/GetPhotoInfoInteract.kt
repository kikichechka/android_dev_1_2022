package com.example.photosights.domain

import android.content.Context
import android.location.Location
import com.example.photosights.data.RepoDatabase
import com.example.photosights.data.RepoMediaStore
import com.example.photosights.data.model.Photo
import com.yandex.mapkit.geometry.Point
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class GetPhotoInfoInteract @Inject constructor(
    private val repoMediaStore: RepoMediaStore,
    private val repoDatabase: RepoDatabase
) {

    fun getAllData(): Flow<List<Photo>> {
        return repoDatabase.getAllData()
    }

    fun getListPoints(): Flow<List<Point>> {
        return MyConverter.convertFromLocationToPoint(repoDatabase.getAllData())
    }

    suspend fun deleteDataByUri(uri: String) {
        repoDatabase.deleteData(uri)
        repoMediaStore.deletePhotoFromDeviceMemoryByUri(uri)
    }

    suspend fun insertData(context: Context, location: Location) {
        val uri = repoMediaStore.getUriLastPhoto(context)
        repoDatabase.insertData(uri, location)
    }

    suspend fun getLastPhoto(context: Context): Photo {
        val uri = repoMediaStore.getUriLastPhoto(context)
        return repoDatabase.getLastPhoto(uri)
    }

    suspend fun deleteLastData(context: Context) {
        val lastUri = repoMediaStore.getUriLastPhoto(context)
        repoMediaStore.deletePhotoFromDeviceMemoryByUri(lastUri)
        repoDatabase.deleteData(lastUri)
    }

    companion object {
        object MyConverter {
            fun convertFromLocationToPoint(listPhoto: Flow<List<Photo>>): Flow<List<Point>> {
                val listPoint = mutableListOf<Point>()
                val scope = CoroutineScope(Dispatchers.Default)

                listPhoto.onEach {
                    it.map { photo -> photo.location }.forEach { location ->
                        if (location != null) {
                            listPoint.add(Point(location.latitude, location.longitude))
                        }
                    }
                }.launchIn(scope)
                return listOf(listPoint).asFlow()
            }
        }
    }
}
