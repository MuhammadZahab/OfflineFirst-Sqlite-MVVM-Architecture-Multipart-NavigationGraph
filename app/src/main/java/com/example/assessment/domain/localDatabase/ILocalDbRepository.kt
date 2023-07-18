package com.example.assessment.domain.localDatabase

import com.example.assessment.data.buying.remoteApi.reponse.BuyingApiResponse
import kotlinx.coroutines.flow.Flow

interface ILocalDbRepository {
    suspend fun callingListLocal(): Flow<List<BuyingApiResponse>>
    suspend fun insertData(buyingResponse: BuyingApiResponse)
}