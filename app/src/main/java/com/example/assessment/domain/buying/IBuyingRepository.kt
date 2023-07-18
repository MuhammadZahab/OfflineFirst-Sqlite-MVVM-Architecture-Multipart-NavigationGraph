package com.example.assessment.domain.buying

import com.example.assessment.data.baseResponse.BaseResponseResult
import com.example.assessment.data.buying.remoteApi.reponse.BuyingApiResponse
import kotlinx.coroutines.flow.Flow

interface IBuyingRepository {
    suspend fun buyingList(): Flow<BaseResponseResult<ArrayList<BuyingApiResponse>, Any>>
}