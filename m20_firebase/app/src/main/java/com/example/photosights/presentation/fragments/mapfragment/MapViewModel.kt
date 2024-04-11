package com.example.photosights.presentation.fragments.mapfragment

import androidx.lifecycle.ViewModel
import com.example.photosights.domain.GetPhotoInfoUseCase
import javax.inject.Inject

class MapViewModel @Inject constructor(getPhotoInfoUseCase: GetPhotoInfoUseCase) : ViewModel() {
    val listPoints = getPhotoInfoUseCase.getListPoints()
}
