package com.example.assessment.data.calling

import com.example.assessment.data.calling.remoteApi.api.ICallingApi
import com.example.assessment.data.calling.repository.CallingRepositoryImpl
import com.example.assessment.data.ApiNetwork.NetworkModule
import com.example.assessment.domain.calling.ICallingRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton


@Module(includes = [NetworkModule::class])
@InstallIn(SingletonComponent::class)
class CallingApiModule {

    @Singleton
    @Provides
    fun provideCallingApi(retrofit: Retrofit): ICallingApi {
        return retrofit.create(ICallingApi::class.java)
    }

    @Singleton
    @Provides
    fun provideCallingRepository(callingApi: ICallingApi): ICallingRepository {
        return CallingRepositoryImpl(callingApi)
    }

}