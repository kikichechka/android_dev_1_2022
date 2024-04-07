package com.example.photosights.presentation.fragments.listphoto

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.photosights.domain.GetPhotoInfoInteract
import kotlinx.coroutines.launch
import javax.inject.Inject

class ListPhotoViewModel @Inject constructor(private val getPhotoInfoInteract: GetPhotoInfoInteract) : ViewModel() {
    val listPhotos = getPhotoInfoInteract.getAllData()

    fun deletePhoto(uri: String) {
        viewModelScope.launch {
            getPhotoInfoInteract.deleteDataByUri(uri)
        }
    }
}
