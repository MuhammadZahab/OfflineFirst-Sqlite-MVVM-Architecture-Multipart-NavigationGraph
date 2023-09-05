package com.assessment.todo.domain.anime.use_cases

import com.assessment.todo.data.anime.remote.model.AnimeResponseModel
import com.assessment.todo.domain.anime.repository.IAnimeRepository

import kotlinx.coroutines.flow.Flow


class GetAnimeListFromRemote(private val animeRepository: IAnimeRepository) {


    suspend fun getAnimeListFromRemote() : Flow<ArrayList<AnimeResponseModel.Data>> {

           return animeRepository.getAnimeListFromRemote()
    }
}