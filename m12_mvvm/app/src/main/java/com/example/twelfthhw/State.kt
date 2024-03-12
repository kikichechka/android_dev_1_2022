package com.example.twelfthhw

sealed class State {
    data object Loading: State()
    data object Success : State()
}
