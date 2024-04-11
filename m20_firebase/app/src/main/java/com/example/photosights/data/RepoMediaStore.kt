package com.example.photosights.data

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import java.io.File
import javax.inject.Inject

class RepoMediaStore @Inject constructor() {
    @SuppressLint("Recycle")
    fun getUriLastPhoto(context: Context): String {
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

    fun deletePhotoFromDeviceMemoryByUri(uri: String) {
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
