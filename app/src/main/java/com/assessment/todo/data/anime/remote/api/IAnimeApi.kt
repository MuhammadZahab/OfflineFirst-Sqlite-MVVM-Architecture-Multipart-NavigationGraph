package com.assessment.todo.data.anime.remote.api

import com.assessment.todo.data.anime.remote.model.AnimeResponseModel
import retrofit2.Response
import retrofit2.http.GET

interface IAnimeApi {

    @GET("/v4/top/anime")
    suspend fun getAnimeList(): Response<AnimeResponseModel>
}