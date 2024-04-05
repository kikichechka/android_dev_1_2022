package com.example.photosights.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject

class MakePhotoViewModelFactory @Inject constructor(
    private val makePhotoViewModel: MakePhotoViewModel
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MakePhotoViewModel::class.java)) {
            return makePhotoViewModel as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}
