package com.example.assessment.data.calling.repository

import com.example.assessment.data.baseResponse.BaseResponseResult
import com.example.assessment.data.calling.remoteApi.api.ICallingApi
import com.example.assessment.data.calling.remoteApi.reponse.CallingApiResponse
import com.example.assessment.domain.calling.ICallingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CallingRepositoryImpl @Inject constructor(private val callingApi: ICallingApi) : ICallingRepository {
    override suspend fun callingList(): Flow<BaseResponseResult<ArrayList<CallingApiResponse>, Any>> {
        return flow {
            val response = callingApi.getCallingList()
            if (response.isSuccessful) {
                emit(BaseResponseResult.Success(response.body()))
            } else {
                emit(BaseResponseResult.Error(response.message()))
            }
        }
    }
}