package com.example.assessment.presentation.calling

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.assessment.data.baseResponse.BaseResponseResult
import com.example.assessment.data.calling.remoteApi.reponse.CallingApiResponse
import com.example.assessment.domain.calling.CallingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CallingViewModel @Inject constructor(private var callingUseCase: CallingUseCase) : ViewModel() {

    private val state = MutableStateFlow<CallingListState>(CallingListState.Init)
    val mState: StateFlow<CallingListState> get() = state

    private fun isLoading() {
        state.value = CallingListState.IsLoading(true)
    }

    private fun exception(message: String) {
        state.value = CallingListState.Exception(message)
    }

    private fun hideLoading() {
        state.value = CallingListState.IsLoading(false)
    }


    fun getCallingList() {
        viewModelScope.launch {
            callingUseCase.calling()
                .onStart {
                    isLoading()
                }
                .catch { exception ->
                    hideLoading()
                }
                .collect { baseResult ->
                    when (baseResult) {
                        is BaseResponseResult.Error -> state.value = CallingListState.Error(baseResult.rawResponse)
                        is BaseResponseResult.Success -> {
                            state.value = CallingListState.Success(baseResult.data!!)
                        }
                    }
                }
        }
    }


    sealed class CallingListState {
        object Init : CallingListState()
        data class IsLoading(val isLoading: Boolean) : CallingListState()
        data class Success(val callingList: ArrayList<CallingApiResponse>) : CallingListState()
        data class Error(val rawResponse: Any) : CallingListState()
        data class Exception(val exception: Any) : CallingListState()
    }
}