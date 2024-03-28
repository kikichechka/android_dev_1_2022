package com.example.sixteenthhw.presentation

import androidx.lifecycle.ViewModel
import com.example.sixteenthhw.data.UsefulActivityDto
import com.example.sixteenthhw.domain.GetUsefulActivityUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private  val getUsefulActivityUseCase: GetUsefulActivityUseCase
) : ViewModel()
{
    private val defaultActivity = UsefulActivityDto("", "", 0)
    private val _activityFlow = MutableStateFlow(defaultActivity)
    val activityFlow = _activityFlow.asStateFlow()

    suspend fun reloadUsefulActivity() {
        _activityFlow.value = getUsefulActivityUseCase.execute()
    }
}
