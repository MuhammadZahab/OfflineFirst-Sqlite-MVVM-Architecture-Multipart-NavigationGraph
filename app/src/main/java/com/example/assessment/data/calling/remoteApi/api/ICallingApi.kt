package com.example.assessment.data.calling.remoteApi.api

import com.example.assessment.data.calling.remoteApi.reponse.CallingApiResponse
import retrofit2.Response
import retrofit2.http.GET

interface ICallingApi {

    @GET("call")
    suspend fun getCallingList(): Response<ArrayList<CallingApiResponse>>
}