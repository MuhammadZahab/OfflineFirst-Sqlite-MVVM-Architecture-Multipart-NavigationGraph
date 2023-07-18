package com.example.assessment.data.ApiNetwork

import android.content.Context
import com.example.assessment.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Singleton
    @Provides
    fun provideRetrofit(okHttp: OkHttpClient): Retrofit {
        return Retrofit.Builder().apply {
            addConverterFactory(GsonConverterFactory.create())
            client(okHttp)
            baseUrl(Constants.BASE_URL)
        }.build()
    }

    @Singleton
    @Provides
    fun provideOkHttp(@ApplicationContext context: Context): OkHttpClient {
        return OkHttpClient.Builder().apply {
            connectTimeout(60, TimeUnit.SECONDS)
            readTimeout(60, TimeUnit.SECONDS)
            writeTimeout(60, TimeUnit.SECONDS)
            addInterceptor { chain ->
                chain.request().newBuilder()
                    .header("Content-Type", "application/json")
                    .build()
                    .let(chain::proceed)
            }
            addInterceptor(httpInterceptor())
        }.build()
    }

    @Singleton
    @Provides
    fun httpInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }
}