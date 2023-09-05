package com.assessment.todo.di.anime

import com.assessment.todo.data.anime.local.dao.AnimeDao
import com.assessment.todo.data.anime.remote.api.IAnimeApi
import com.assessment.todo.data.anime.repository.AnimeRepositoryImpl
import com.assessment.todo.data.upload_pic.remote.api.IUploadpicApi
import com.assessment.todo.data.upload_pic.repository.UploadPictureRepositoryImpl
import com.assessment.todo.di.network.NetworkModule
import com.assessment.todo.domain.anime.repository.IAnimeRepository
import com.assessment.todo.domain.anime.use_cases.AnimeUseCases
import com.assessment.todo.domain.anime.use_cases.DeleteAnimeListFromDB
import com.assessment.todo.domain.anime.use_cases.GetAnimeListFromDB
import com.assessment.todo.domain.anime.use_cases.GetAnimeListFromRemote
import com.assessment.todo.domain.anime.use_cases.InsertAnimeListInDB
import com.assessment.todo.domain.upload_pic.repository.IUploadPictureRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton


@Module(includes = [NetworkModule::class])
@InstallIn(SingletonComponent::class)
class AnimeApiModule {

    @Singleton
    @Provides
    fun provideAnimeApi(retrofit: Retrofit): IAnimeApi {
        return retrofit.create(IAnimeApi::class.java)
    }

    @Singleton
    @Provides
    fun provideUploadPictureeApi(retrofit: Retrofit): IUploadpicApi {
        return retrofit.create(IUploadpicApi::class.java)
    }

    @Singleton
    @Provides
    fun provideAnimeRepository(animeApi: IAnimeApi, animeDao: AnimeDao): IAnimeRepository {
        return AnimeRepositoryImpl(animeApi,animeDao)
    }

    @Singleton
    @Provides
    fun provideUploadPictureRepository(iUploadpicApi: IUploadpicApi): IUploadPictureRepository {
        return UploadPictureRepositoryImpl(iUploadpicApi)
    }

//
//    @Provides
//    @Singleton
//    fun provideNoteRepository(animeApi: IAnimeApi,animeDao: AnimeDao): IAnimeRepository {
//        return AnimeRepositoryImpl(animeApi,animeDao)
//    }
    @Provides
    @Singleton
    fun provideAnimeUseCases(repository: IAnimeRepository): AnimeUseCases {
        return AnimeUseCases(
            getAnimeListFromRemote = GetAnimeListFromRemote(repository),
            getAnimeListFromDB = GetAnimeListFromDB(repository),
            insertAnimeListInDB = InsertAnimeListInDB(repository),
            deleteAnimeListFromDB = DeleteAnimeListFromDB(repository)

            )
    }

}