package com.example.seventeenthhw.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject

class ListPhotoViewModelFactory @Inject constructor(private val listPhotoViewModel: ListPhotoViewModel) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ListPhotoViewModel::class.java)) {
            return listPhotoViewModel as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}
