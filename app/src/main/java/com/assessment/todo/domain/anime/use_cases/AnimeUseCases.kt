package com.assessment.todo.domain.anime.use_cases

data class AnimeUseCases (
    val getAnimeListFromRemote: GetAnimeListFromRemote,
    val getAnimeListFromDB: GetAnimeListFromDB,
    val insertAnimeListInDB: InsertAnimeListInDB,
    val deleteAnimeListFromDB: DeleteAnimeListFromDB
)