package com.assessment.todo.domain.upload_pic.usecase

import com.assessment.todo.data.upload_pic.remote.model.ImageUploadResponseModel
import com.assessment.todo.data.upload_pic.remote.model.UploadRequestBody
import com.assessment.todo.domain.upload_pic.repository.IUploadPictureRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import javax.inject.Inject

class UploadPictureUseCase @Inject constructor(private val iUploadPictureRepository: IUploadPictureRepository) {

  suspend fun uploadPicture(url:String,part: File): Flow<ImageUploadResponseModel> {

         return iUploadPictureRepository.uploadPicture(url,part)

    }
}