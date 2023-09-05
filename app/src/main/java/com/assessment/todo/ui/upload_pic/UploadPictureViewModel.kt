package com.assessment.todo.ui.upload_pic

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.assessment.todo.data.upload_pic.remote.model.ImageUploadResponseModel
import com.assessment.todo.domain.upload_pic.usecase.UploadPictureUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import javax.inject.Inject

@HiltViewModel
class UploadPictureViewModel @Inject constructor(private var uploadPictureUseCase: UploadPictureUseCase) : ViewModel() {

    private val state = MutableStateFlow<UploadPictureState>(UploadPictureState.Init)
    val mState: StateFlow<UploadPictureState> get() = state

    private fun isLoading() {
        state.value = UploadPictureState.IsLoading(true)
    }

    private fun exception(message: String) {
        state.value = UploadPictureState.Exception(message)
    }

    private fun hideLoading() {
        state.value = UploadPictureState.IsLoading(false)
    }


    fun uploadPicture(url:String, part: File) {

            viewModelScope.launch {
                uploadPictureUseCase.uploadPicture(url,part)
                    .onStart {
                        isLoading()
                    }
                    .catch { exception ->
                        Log.d("exception_image",exception.message.toString())
                        hideLoading()
                    }
                    .collect {

                            state.value = UploadPictureState.Success(it)

                    }
            }

    }

    sealed class UploadPictureState {
        object Init : UploadPictureState()
        data class IsLoading(val isLoading: Boolean) : UploadPictureState()
        data class Success(val imageUploadResponseModel: ImageUploadResponseModel) : UploadPictureState()
        data class Error(val rawResponse: Any) : UploadPictureState()
        data class Exception(val exception: Any) : UploadPictureState()
    }
}