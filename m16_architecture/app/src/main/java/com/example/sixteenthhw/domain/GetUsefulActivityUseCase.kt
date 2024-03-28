package com.example.sixteenthhw.domain

import com.example.sixteenthhw.data.UsefulActivitiesRepository
import com.example.sixteenthhw.data.UsefulActivityDto
import javax.inject.Inject

class GetUsefulActivityUseCase @Inject constructor(
    private val usefulActivitiesRepository: UsefulActivitiesRepository
) {
    suspend fun execute(): UsefulActivityDto {
        return usefulActivitiesRepository.getUsefulActivity()
    }
}
