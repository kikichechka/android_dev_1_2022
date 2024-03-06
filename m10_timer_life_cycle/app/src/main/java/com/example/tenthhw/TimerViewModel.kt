package com.example.tenthhw

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TimerViewModel : ViewModel() {
    private var scope = CoroutineScope(Job())
    private var startNum = 0
    private val _state = MutableStateFlow(State.STARTED)
    val state = _state.asStateFlow()

    private val _progress = MutableStateFlow(0)
    val progress = _progress.asStateFlow()

    private val _count = MutableStateFlow(0)
    val count = _count.asStateFlow()

    fun click() {
        when (state.value) {
            State.STARTED -> start()
            State.STOPPED -> stop()
        }
    }

    private fun start() {
        scope.launch {
            _state.value = State.STOPPED
            val timeDelay: Double = startNum.toDouble() / 100 * 1000

            launch {
                repeat(100) {
                    _progress.value++
                    delay(timeDelay.toLong())
                }
                _progress.value = 0
            }

            launch {
                repeat(count.value) {
                    delay(1000)
                    _count.value--
                }
                delay(500)
                _count.value = startNum
                _state.value = State.STARTED
            }
        }
    }

    private fun stop() {
        scope.cancel()
        scopeInit()
        _state.value = State.STARTED
    }

    fun changeCount(num: Int) {
        stop()
        _progress.value = 0
        _count.value = num
        startNum = num
    }

    private fun scopeInit() {
        scope = CoroutineScope(Job())
    }
}
