package com.example.photosights.data

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import com.example.photosights.model.Photo
import kotlinx.coroutines.flow.Flow
import java.io.File
import javax.inject.Inject

class Repository @Inject constructor(private val photoDao: PhotoDao) {

    fun getAllData(): Flow<List<Photo>> {
        return photoDao.getAll()
    }

    suspend fun deleteLastData(context: Context) {
        val lastUri = getUriLastPhoto(context)
        photoDao.delete(lastUri)
        deletePhotoFromDeviceMemoryByUri(lastUri)
    }

    suspend fun deleteDataByUri(uri: String) {
        photoDao.delete(uri)
        deletePhotoFromDeviceMemoryByUri(uri)
    }

    suspend fun insertData(context: Context) {
        photoDao.insert(Photo(getUriLastPhoto(context)))
    }

    @SuppressLint("Recycle")
    private fun getUriLastPhoto(context: Context): String {
        var returnedUri = ""
        val uri: Uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val projection =
            arrayOf(MediaStore.MediaColumns.DATA, MediaStore.Images.Media.BUCKET_DISPLAY_NAME)
        val cursor = context.contentResolver.query(
            uri, projection, null,
            null, null
        )
        val columnIndexData = cursor!!.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA)

        while (cursor.moveToNext()) {
            if (cursor.isLast) {
                returnedUri = cursor.getString(columnIndexData)
            }
        }
        return returnedUri
    }

    private fun deletePhotoFromDeviceMemoryByUri(uri: String) {
        deleteFile(File(uri))
    }

    private fun deleteFile(file: File) {
        if (file.exists()) {
            if (file.delete()) {
                Log.d("@@@", "file Deleted : ${file.path}")
            } else {
                Log.d("@@@", "file not Deleted :${file.path}")
            }
        }
    }
}
