package com.example.assessment.domain.calling

import com.example.assessment.data.baseResponse.BaseResponseResult
import com.example.assessment.data.calling.remoteApi.reponse.CallingApiResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CallingUseCase @Inject constructor(private val callingRepository: ICallingRepository) {
    suspend fun calling(): Flow<BaseResponseResult<ArrayList<CallingApiResponse>, Any>> {
        return callingRepository.callingList()
    }
}