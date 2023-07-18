package com.example.assessment.data.buying

import com.example.assessment.data.buying.remoteApi.api.IBuyingApi
import com.example.assessment.data.buying.repository.BuyingRepositoryImpl
import com.example.assessment.data.ApiNetwork.NetworkModule
import com.example.assessment.domain.buying.IBuyingRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton


@Module(includes = [NetworkModule::class])
@InstallIn(SingletonComponent::class)
class BuyingApiModule {

    @Singleton
    @Provides
    fun provideBuyingApi(retrofit: Retrofit): IBuyingApi {
        return retrofit.create(IBuyingApi::class.java)
    }

    @Singleton
    @Provides
    fun provideBuyingRepository(buyingApi: IBuyingApi): IBuyingRepository {
        return BuyingRepositoryImpl(buyingApi)
    }

}