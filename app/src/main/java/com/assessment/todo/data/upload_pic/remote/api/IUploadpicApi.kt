package com.assessment.todo.data.upload_pic.remote.api

import android.media.Image
import com.assessment.todo.data.anime.remote.model.AnimeResponseModel
import com.assessment.todo.data.upload_pic.remote.model.ImageUploadResponseModel
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Url
import java.io.File


interface IUploadpicApi {

    @Multipart
    @POST
    fun uploadImage(
        @Url url:String,
        @Part image: MultipartBody.Part
    ): Response<ImageUploadResponseModel>


}


