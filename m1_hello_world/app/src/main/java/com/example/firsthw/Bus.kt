package com.example.firsthw

class Bus {
    private var _numberOfSeats = 0
    val numberOfSeats: Int
        get() {
            return _numberOfSeats
        }

    private var _fullnessType: FullnessType = FullnessType.FREE
    val fullnessType: FullnessType
        get() {
            return _fullnessType
        }
    val maxSpace = 49

    fun addPassenger(): FullnessType {
        _numberOfSeats += 1
        return checkFullness()
    }

    fun removePassenger(): FullnessType {
        _numberOfSeats -= 1
        return checkFullness()
    }

    private fun checkFullness(): FullnessType {
        return when (_numberOfSeats) {
            0 -> FullnessType.FREE
            in 1..49 -> FullnessType.FILLED
            else -> FullnessType.CROWDED
        }
    }
}