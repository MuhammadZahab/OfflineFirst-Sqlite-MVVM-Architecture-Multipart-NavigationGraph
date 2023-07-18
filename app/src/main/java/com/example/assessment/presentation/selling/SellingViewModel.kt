package com.example.assessment.presentation.selling

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.assessment.data.buying.remoteApi.reponse.BuyingApiResponse
import com.example.assessment.domain.localDatabase.CallingLocalDbUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SellingViewModel @Inject constructor(private val callingLocalDbUseCase: CallingLocalDbUseCase) :
    ViewModel() {

    private val state = MutableStateFlow<SellingListState>(SellingListState.Init)
    val mState: StateFlow<SellingListState> get() = state

    private fun isLoading() {
        state.value = SellingListState.IsLoading(true)
    }

    private fun exception(message: String) {
        state.value = SellingListState.Exception(message)
    }

    private fun hideLoading() {
        state.value = SellingListState.IsLoading(false)
    }

    init {
        viewModelScope.launch {
            insertLocalSellList().forEach {
                callingLocalDbUseCase.insertingData(it)
            }
        }
    }

    fun getSellListFromLocal() {
        viewModelScope.launch {
            callingLocalDbUseCase.calling()
                .onStart {
                    isLoading()
                }
                .catch { exception ->
                    hideLoading()
                }
                .collect { list ->
                    val arrayList = ArrayList<BuyingApiResponse>()
                    list.toCollection(arrayList)
                    state.value = SellingListState.Success(arrayList)
                }
        }
    }


    private fun insertLocalSellList(): ArrayList<BuyingApiResponse> {
        val localSellList = arrayListOf<BuyingApiResponse>()
        localSellList.add(BuyingApiResponse(2, "TV", 38000, 2, 2))
        localSellList.add(BuyingApiResponse(1, "IPhone", 150000, 1, 2))
        localSellList.add(BuyingApiResponse(3, "Table", 12000, 1, 2))

        return localSellList
    }

    sealed class SellingListState {
        object Init : SellingListState()
        data class IsLoading(val isLoading: Boolean) : SellingListState()
        data class Success(val sellingList: ArrayList<BuyingApiResponse>) : SellingListState()
        data class Error(val rawResponse: Any) : SellingListState()
        data class Exception(val exception: Any) : SellingListState()
    }
}