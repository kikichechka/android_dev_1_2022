package com.example.photosights.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.photosights.data.Repository
import kotlinx.coroutines.launch
import javax.inject.Inject

class ListPhotoViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    val listPhotos = repository.getAllData()

    fun deletePhoto(uri: String) {
        viewModelScope.launch {
            repository.deleteDataByUri(uri)
        }
    }
}
