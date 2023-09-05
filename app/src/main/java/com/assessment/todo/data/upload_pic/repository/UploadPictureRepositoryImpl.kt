package com.assessment.todo.data.upload_pic.repository

import android.media.Image
import android.util.Log
import com.assessment.todo.data.upload_pic.remote.api.IUploadpicApi
import com.assessment.todo.data.upload_pic.remote.model.ImageUploadResponseModel
import com.assessment.todo.data.upload_pic.remote.model.UploadRequestBody
import com.assessment.todo.domain.upload_pic.repository.IUploadPictureRepository
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File


class UploadPictureRepositoryImpl(private val iUploadpicApi: IUploadpicApi): IUploadPictureRepository {

    override suspend fun uploadPicture(
        url:String,
        part:File
    ): Flow<ImageUploadResponseModel> {
        return flow {

            var filePart:MultipartBody.Part  =
            MultipartBody.Part.createFormData(
            "image",
            part.name,
            part.asRequestBody("multipart/form-data".toMediaTypeOrNull()))


            var response = iUploadpicApi.uploadImage(url,filePart)

            if (response.isSuccessful) {
                response.body()?.let { emit(it) }
            }

        }
    }
    }


