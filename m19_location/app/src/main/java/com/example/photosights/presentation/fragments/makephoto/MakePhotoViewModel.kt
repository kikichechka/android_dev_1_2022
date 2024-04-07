package com.example.photosights.presentation.fragments.makephoto

import android.content.Context
import android.location.Location
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.photosights.domain.GetPhotoInfoInteract
import kotlinx.coroutines.launch
import javax.inject.Inject

class MakePhotoViewModel @Inject constructor(private val getPhotoInfoInteract: GetPhotoInfoInteract) : ViewModel() {

    fun insertPhoto(context: Context, location: Location) {
        viewModelScope.launch {
            getPhotoInfoInteract.insertData(context, location)
        }
    }
}
