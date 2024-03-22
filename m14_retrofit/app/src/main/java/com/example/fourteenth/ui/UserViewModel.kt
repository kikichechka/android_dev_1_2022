package com.example.fourteenth.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fourteenth.repo.UserRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class UserViewModel : ViewModel() {
    private val userRepository: UserRepository = UserRepository()

    private val _state: MutableStateFlow<State> = MutableStateFlow(State.Loading)
    val state = _state.asStateFlow()

    private val _user: MutableStateFlow<UsersModels?> = MutableStateFlow(null)
    val user = _user.asStateFlow()

    private val _error = Channel<String>()
    val error = _error.receiveAsFlow()

    fun getUser() {
        _state.value = State.Loading
        viewModelScope.launch {
            when (val searchUser = userRepository.loadUser()) {
                null -> {
                    _state.value = State.Error()
                    _error.send((_state.value as State.Error).nameError)
                }

                else -> {
                    _user.value = searchUser
                    _state.value = State.Success
                }
            }
        }
    }
}
