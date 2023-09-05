package com.assessment.todo.ui.anime

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.assessment.todo.data.anime.remote.model.AnimeResponseModel
import com.assessment.todo.domain.anime.use_cases.AnimeUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnimeViewModel @Inject constructor(private var animeUseCase: AnimeUseCases) : ViewModel() {

    private val state = MutableStateFlow<AnimeListState>(AnimeListState.Init)
    val mState: StateFlow<AnimeListState> get() = state

    private fun isLoading() {
        state.value = AnimeListState.IsLoading(true)
    }

    private fun exception(message: String) {
        state.value = AnimeListState.Exception(message)
    }

    private fun hideLoading() {
        state.value = AnimeListState.IsLoading(false)
    }

    fun removeDataFromDatabase(){

        viewModelScope.launch {

            animeUseCase.deleteAnimeListFromDB.deleteAllRecordsFromDB()

        }
    }

    fun getAnimeList() {


            viewModelScope.launch {
                animeUseCase.getAnimeListFromRemote.getAnimeListFromRemote()

                    .onStart {
                        isLoading()
                    }
                    .catch { exception ->
                        hideLoading()
                    }
                    .collect {

                            state.value = AnimeListState.Success(it)

                    }
            }

    }

    sealed class AnimeListState {
        object Init : AnimeListState()
        data class IsLoading(val isLoading: Boolean) : AnimeListState()
        data class Success(val animeList: ArrayList<AnimeResponseModel.Data>) : AnimeListState()
        data class Error(val rawResponse: Any) : AnimeListState()
        data class Exception(val exception: Any) : AnimeListState()
    }
}