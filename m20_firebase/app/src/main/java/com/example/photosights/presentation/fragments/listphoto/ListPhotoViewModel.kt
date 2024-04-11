package com.example.photosights.presentation.fragments.listphoto

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.photosights.domain.GetPhotoInfoUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class ListPhotoViewModel @Inject constructor(private val getPhotoInfoUseCase: GetPhotoInfoUseCase) : ViewModel() {
    val listPhotos = getPhotoInfoUseCase.getAllData()

    fun deletePhoto(uri: String) {
        viewModelScope.launch {
            getPhotoInfoUseCase.deleteDataByUri(uri)
        }
    }
}
