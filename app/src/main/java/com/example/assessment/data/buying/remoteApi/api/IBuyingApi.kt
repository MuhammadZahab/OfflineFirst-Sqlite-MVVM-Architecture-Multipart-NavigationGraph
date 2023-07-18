package com.example.assessment.data.buying.remoteApi.api

import com.example.assessment.data.buying.remoteApi.reponse.BuyingApiResponse
import retrofit2.Response
import retrofit2.http.GET

interface IBuyingApi {

    @GET("buy")
    suspend fun getBuyingList(): Response<ArrayList<BuyingApiResponse>>
}