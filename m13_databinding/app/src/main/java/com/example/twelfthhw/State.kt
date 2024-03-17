package com.example.twelfthhw

sealed class State(
    open val countError: String? = null
) {
    data object Loading : State()
    data object Success : State()
    data class Error(override val countError: String = "Введите не менее 3-х символов") : State(countError = countError)
}
