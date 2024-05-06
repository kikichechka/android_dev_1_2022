package com.example.seventeenthhw.presentation

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.seventeenthhw.data.model.AstronomyPicture
import com.example.seventeenthhw.data.ListMars
import com.example.seventeenthhw.data.Repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import javax.inject.Inject

class ListPhotoViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    @SuppressLint("SimpleDateFormat")
    private val dateFormat = SimpleDateFormat(DATE_FORMAT)

    private val _stateLoad = MutableStateFlow(false)
    val stateLoad = _stateLoad.asStateFlow()

    private val _listPhotoMars = MutableStateFlow(ListMars(listOf()))
    val listPhotoMars = _listPhotoMars.asStateFlow()

    private val _listPhotoAstronomyPicture = MutableStateFlow<List<AstronomyPicture>>(listOf())
    val listPhotoAstronomyPicture = _listPhotoAstronomyPicture.asStateFlow()

    private val _datePhotos = MutableStateFlow(START_DATE)
    val datePhotos = _datePhotos.asStateFlow()

    private val _maxDatePhotos = MutableStateFlow(END_DATE)

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            _stateLoad.value = true
            _listPhotoMars.value = repository.loadMars(_datePhotos.value)
        }
        viewModelScope.launch {
            _listPhotoAstronomyPicture.value = repository.loadAstronomy(_datePhotos.value, _maxDatePhotos.value)
            _stateLoad.value = false
        }
    }

    fun changeDate(calendar: Calendar) {
        var date = calendar.time
        _datePhotos.value = dateFormat.format(date)
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 1)
        date = calendar.time
        _maxDatePhotos.value = dateFormat.format(date)
        loadData()
    }

    companion object {
        private const val START_DATE = "2024-01-01"
        private const val DATE_FORMAT = "yyyy-MM-dd"

        private const val END_DATE = "2024-02-01"
    }
}
