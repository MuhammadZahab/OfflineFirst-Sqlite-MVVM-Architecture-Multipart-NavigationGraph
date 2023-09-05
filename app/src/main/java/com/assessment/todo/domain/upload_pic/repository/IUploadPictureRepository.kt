package com.assessment.todo.domain.upload_pic.repository

import com.assessment.todo.data.upload_pic.remote.model.ImageUploadResponseModel
import kotlinx.coroutines.flow.Flow
import java.io.File

interface IUploadPictureRepository {

    suspend fun uploadPicture(url:String,part:File): Flow<ImageUploadResponseModel>

}