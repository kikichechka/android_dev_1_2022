package com.example.photosights.presentation.fragments.itemdone

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.photosights.domain.GetPhotoInfoInteract
import com.example.photosights.data.model.Photo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class ItemDonePhotoViewModel @Inject constructor(private val getPhotoInfoInteract: GetPhotoInfoInteract) :
    ViewModel() {
    val data = MutableStateFlow(Photo("", null))

    fun getData(context: Context) {
        viewModelScope.launch {
            data.value = getPhotoInfoInteract.getLastPhoto(context)
        }
    }

    fun deleteData(context: Context) {
        viewModelScope.launch {
            getPhotoInfoInteract.deleteLastData(context)
        }
    }
}
