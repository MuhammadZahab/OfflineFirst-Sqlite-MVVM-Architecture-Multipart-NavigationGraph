package com.example.assessment.data.buying.repository

import com.example.assessment.data.baseResponse.BaseResponseResult
import com.example.assessment.data.buying.remoteApi.api.IBuyingApi
import com.example.assessment.data.buying.remoteApi.reponse.BuyingApiResponse
import com.example.assessment.domain.buying.IBuyingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class BuyingRepositoryImpl @Inject constructor(private val buyingApi : IBuyingApi) : IBuyingRepository {
    override suspend fun buyingList(): Flow<BaseResponseResult<ArrayList<BuyingApiResponse>, Any>> {
        return flow {
            val response = buyingApi.getBuyingList()
            if (response.isSuccessful) {
                emit(BaseResponseResult.Success(response.body()))
            } else {
                emit(BaseResponseResult.Error(response.message()))
            }
        }
    }
}