package com.example.twelfthhw

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.twelfthhw.repo.Repository
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class SearchViewModel : ViewModel() {
    private val repository = Repository()
    val state = MutableStateFlow<State>(State.Success)
    private val enteredStr = MutableStateFlow("")
    val resultStr = MutableStateFlow("")
    private var searchJob: Job? = null

    private suspend fun searchString() {
        state.value = State.Loading
        resultStr.value =
            repository.searchString(enteredStr.value)
                ?: "По запросу \"${enteredStr.value}\" ничего не найдено"
        state.value = State.Success
    }

    @OptIn(FlowPreview::class)
    fun onTextChanged(str: String) {
        searchJob?.cancel()
        if (str.length < 3)
            state.value = State.Error()
        else {
            enteredStr.value = str
            searchJob = enteredStr
                .debounce(1000)
                .onEach {
                    searchString()
                }
                .launchIn(viewModelScope)
        }
    }
}
