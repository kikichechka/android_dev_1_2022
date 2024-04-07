package com.example.photosights.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.photosights.presentation.fragments.itemdone.ItemDonePhotoViewModel
import com.example.photosights.presentation.fragments.listphoto.ListPhotoViewModel
import com.example.photosights.presentation.fragments.makephoto.MakePhotoViewModel
import com.example.photosights.presentation.fragments.mapfragment.MapViewModel
import javax.inject.Inject

class ViewModelFactory @Inject constructor(
    private val makePhotoViewModel: MakePhotoViewModel,
    private val mapViewModel: MapViewModel,
    private val listPhotoViewModel: ListPhotoViewModel,
    private val itemDonePhotoViewModel: ItemDonePhotoViewModel
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MakePhotoViewModel::class.java)) {
            return makePhotoViewModel as T
        }
        if (modelClass.isAssignableFrom(MapViewModel::class.java)) {
            return mapViewModel as T
        }
        if (modelClass.isAssignableFrom(ListPhotoViewModel::class.java)) {
            return listPhotoViewModel as T
        }
        if (modelClass.isAssignableFrom(ItemDonePhotoViewModel::class.java)) {
            return itemDonePhotoViewModel as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}
