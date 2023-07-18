package com.example.assessment.presentation.buying

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.assessment.data.baseResponse.BaseResponseResult
import com.example.assessment.data.buying.remoteApi.reponse.BuyingApiResponse
import com.example.assessment.domain.buying.BuyingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BuyingViewModel @Inject constructor(private var buyingUseCase: BuyingUseCase) : ViewModel() {

    private val state = MutableStateFlow<BuyingListState>(BuyingListState.Init)
    val mState: StateFlow<BuyingListState> get() = state

    private fun isLoading() {
        state.value = BuyingListState.IsLoading(true)
    }

    private fun exception(message: String) {
        state.value = BuyingListState.Exception(message)
    }

    private fun hideLoading() {
        state.value = BuyingListState.IsLoading(false)
    }


    fun getBuyingList() {
        viewModelScope.launch {
            buyingUseCase.buying()
                .onStart {
                    isLoading()
                }
                .catch { exception ->
                    hideLoading()
                }
                .collect { baseResult ->
                    when (baseResult) {
                        is BaseResponseResult.Error -> state.value =
                            BuyingListState.Error(baseResult.rawResponse)
                        is BaseResponseResult.Success -> {
                            state.value = BuyingListState.Success(baseResult.data!!)
                        }
                    }
                }
        }
    }


    sealed class BuyingListState {
        object Init : BuyingListState()
        data class IsLoading(val isLoading: Boolean) : BuyingListState()
        data class Success(val buyingList: ArrayList<BuyingApiResponse>) : BuyingListState()
        data class Error(val rawResponse: Any) : BuyingListState()
        data class Exception(val exception: Any) : BuyingListState()
    }
}