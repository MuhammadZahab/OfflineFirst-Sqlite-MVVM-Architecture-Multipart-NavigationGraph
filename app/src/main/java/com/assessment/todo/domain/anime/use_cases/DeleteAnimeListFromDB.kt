package com.assessment.todo.domain.anime.use_cases

import com.assessment.todo.data.anime.remote.model.AnimeResponseModel
import com.assessment.todo.domain.anime.repository.IAnimeRepository
import javax.inject.Inject

class DeleteAnimeListFromDB(private val animeRepository: IAnimeRepository) {

    suspend fun deleteAllRecordsFromDB() {
        animeRepository.deleteAnimeListFromDB()
    }

}