package com.example.assessment.data.localDatabase.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.assessment.data.buying.remoteApi.reponse.BuyingApiResponse
import kotlinx.coroutines.flow.Flow

@Dao
interface ISellDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSell(buyingResponse: BuyingApiResponse)

    @Query("Select * from ItemToSell")
    fun getAllSellList(): Flow<List<BuyingApiResponse>>

}