package com.example.photosights.presentation

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.photosights.data.Repository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class MakePhotoViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    private val _state = MutableStateFlow(StateMakePhotoFragment.MAKE_PHOTO)
    val stateFragment = _state.asStateFlow()

    fun insertPhoto(context: Context) {
        viewModelScope.launch {
            repository.insertData(context)
        }
    }

    fun deletePhoto(context: Context) {
        viewModelScope.launch {
            repository.deleteLastData(context)
        }
    }

    fun takePicture() {
        viewModelScope.launch {
            delay(1000)
            _state.value = StateMakePhotoFragment.READY_PHOTO
        }
    }

    fun remakePhoto() {
        _state.value = StateMakePhotoFragment.MAKE_PHOTO
    }
}
