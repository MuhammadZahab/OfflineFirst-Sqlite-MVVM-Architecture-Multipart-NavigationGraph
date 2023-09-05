package com.assessment.todo.data.anime.remote.model

data class AnimeResponseModel(
    val data: ArrayList<Data> = arrayListOf<Data>()
) {
    data class Data(

        val title: String? = "",
        val title_english: String? = "",

        )
}
