package com.example.photosights.presentation.fragments.mapfragment

import androidx.lifecycle.ViewModel
import com.example.photosights.domain.GetPhotoInfoInteract
import javax.inject.Inject

class MapViewModel @Inject constructor(getPhotoInfoInteract: GetPhotoInfoInteract) : ViewModel() {
    val listPoints = getPhotoInfoInteract.getListPoints()
}
