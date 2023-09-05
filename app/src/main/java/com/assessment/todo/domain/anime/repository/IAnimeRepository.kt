package com.assessment.todo.domain.anime.repository

import com.assessment.todo.data.anime.remote.model.AnimeResponseModel
import kotlinx.coroutines.flow.Flow

interface IAnimeRepository {


    suspend fun getAnimeListFromRemote():Flow<ArrayList<AnimeResponseModel.Data>>
    suspend fun getAnimeListFromDB(): Flow<ArrayList<AnimeResponseModel.Data>>
    suspend fun insertAnimeListInDB(animeList: ArrayList<AnimeResponseModel.Data>)

    suspend fun deleteAnimeListFromDB()

}