package com.example.twelfthhw

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.twelfthhw.repo.Repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {
    private val repository = Repository()
    private val _state = MutableStateFlow<State>(State.Success)
    val state = _state.asStateFlow()
    private val _searchString = MutableStateFlow("")
    val searchString = _searchString.asStateFlow()

    fun searchString(str: String) {
        viewModelScope.launch {
            _state.value = State.Loading
            _searchString.value = repository.searchString(str)
                ?: "По запросу \"${str}\" ничего не найдено"
            _state.value = State.Success
        }
    }
}
