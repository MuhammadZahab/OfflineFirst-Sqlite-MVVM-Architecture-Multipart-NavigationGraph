package com.assessment.todo.di.anime

import android.content.Context
import com.assessment.todo.data.anime.local.DatabaseOpenHelper
import com.assessment.todo.data.anime.local.dao.AnimeDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AnimeDbModule {

    @Provides
    @Singleton
    fun provideLocalDatabase(@ApplicationContext app : Context):DatabaseOpenHelper{
        return DatabaseOpenHelper(app)
    }

    @Singleton
    @Provides
    fun provideAnimDao(databaseOpenHelper: DatabaseOpenHelper): AnimeDao {
        return AnimeDao(databaseOpenHelper)
    }



}