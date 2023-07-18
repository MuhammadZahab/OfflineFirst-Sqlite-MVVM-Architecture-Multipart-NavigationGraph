package com.example.assessment.domain.buying

import com.example.assessment.data.baseResponse.BaseResponseResult
import com.example.assessment.data.buying.remoteApi.reponse.BuyingApiResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BuyingUseCase @Inject constructor(private val buyingRepository: IBuyingRepository) {
    suspend fun buying(): Flow<BaseResponseResult<ArrayList<BuyingApiResponse>, Any>> {
        return buyingRepository.buyingList()
    }
}