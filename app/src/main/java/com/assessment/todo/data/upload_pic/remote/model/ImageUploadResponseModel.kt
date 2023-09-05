package com.assessment.todo.data.upload_pic.remote.model

data class ImageUploadResponseModel(
    val error: String? = "",
    val frameCount: Int? = 0,
    val result: List<Result?>? = listOf()
) {
    data class Result(
        val anilist: Int? = 0,
        val episode: Any? = Any(),
        val filename: String? = "",
        val from: Double? = 0.0,
        val image: String? = "",
        val similarity: Double? = 0.0,
        val to: Double? = 0.0,
        val video: String? = ""
    )

}
