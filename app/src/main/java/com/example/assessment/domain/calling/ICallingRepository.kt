package com.example.assessment.domain.calling

import com.example.assessment.data.baseResponse.BaseResponseResult
import com.example.assessment.data.calling.remoteApi.reponse.CallingApiResponse
import kotlinx.coroutines.flow.Flow

interface ICallingRepository {
    suspend fun callingList(): Flow<BaseResponseResult<ArrayList<CallingApiResponse>, Any>>
}