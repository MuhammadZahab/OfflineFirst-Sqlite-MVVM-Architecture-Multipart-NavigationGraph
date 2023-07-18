package com.example.assessment.data.localDatabase.repository

import com.example.assessment.data.buying.remoteApi.reponse.BuyingApiResponse
import com.example.assessment.data.localDatabase.db.LocalDb
import com.example.assessment.domain.localDatabase.ILocalDbRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CallingLocalDbRepositoryImpl @Inject constructor(private val localDb: LocalDb) : ILocalDbRepository {
    override suspend fun callingListLocal(): Flow<List<BuyingApiResponse>> {
        return localDb.getSellDao().getAllSellList()
    }

    override suspend fun insertData(buyingResponse: BuyingApiResponse) {
        localDb.getSellDao().insertSell(buyingResponse)
    }

}