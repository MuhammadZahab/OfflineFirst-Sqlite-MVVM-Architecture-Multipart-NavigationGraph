package com.example.assessment.data.localDatabase.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.assessment.data.buying.remoteApi.reponse.BuyingApiResponse
import com.example.assessment.data.localDatabase.dao.ISellDao

@Database(entities = [BuyingApiResponse::class], version = 1)
abstract class LocalDb : RoomDatabase() {
    
    abstract fun getSellDao(): ISellDao

}