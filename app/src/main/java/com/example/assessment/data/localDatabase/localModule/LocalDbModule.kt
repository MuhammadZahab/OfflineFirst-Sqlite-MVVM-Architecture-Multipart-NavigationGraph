package com.example.assessment.data.localDatabase.localModule

import android.content.Context
import androidx.room.Room
import com.example.assessment.data.localDatabase.db.LocalDb
import com.example.assessment.data.localDatabase.repository.CallingLocalDbRepositoryImpl
import com.example.assessment.domain.localDatabase.ILocalDbRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalDbModule {

    @Provides
    @Singleton
    fun provideLocalDatabase(@ApplicationContext app : Context) = Room.databaseBuilder(app,
        LocalDb::class.java,"Db").build()

    @Singleton
    @Provides
    fun provideSellDaoRepository(localDb: LocalDb): ILocalDbRepository {
        return CallingLocalDbRepositoryImpl(localDb)
    }

}