package com.example.fifteenthhw.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class DataViewModel(private val wordDao: WordDao): ViewModel() {
    private val _state = MutableStateFlow(State.START)
    val state = _state.asStateFlow()

    val getAllWithLimit = this.wordDao.getAllWithLimit().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000L),
        emptyList()
    )

    fun onSave(enteredWord:String) {
        viewModelScope.launch {
            when(wordDao.getWord(enteredWord)) {
                null -> wordDao.addWord(Word(enteredWord, 1))
                else -> wordDao.update(enteredWord)
            }
        }
    }

    fun changeState(text: CharSequence?) {
        if (!text.isNullOrEmpty() && text.matches(Regex("""^[А-Яа-я\-]{3,}${'$'}""")))
            _state.value = State.SUCCESS
        else _state.value = State.ERROR
    }

    fun onDelete() {
        viewModelScope.launch {
            wordDao.delete()
        }
    }
}
