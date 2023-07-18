package com.example.assessment.domain.localDatabase

import com.example.assessment.data.buying.remoteApi.reponse.BuyingApiResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CallingLocalDbUseCase @Inject constructor(private val localDbRepository: ILocalDbRepository) {
    suspend fun calling(): Flow<List<BuyingApiResponse>> {
        return localDbRepository.callingListLocal()
    }

    suspend fun insertingData(buyingResponse: BuyingApiResponse) {
        localDbRepository.insertData(buyingResponse)
    }
}