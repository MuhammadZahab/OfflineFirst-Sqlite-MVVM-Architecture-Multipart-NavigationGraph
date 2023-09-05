package com.assessment.todo.ui.anime

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import com.assessment.todo.data.anime.remote.model.AnimeResponseModel
import com.assessment.todo.databinding.FragmentAnimeBinding
import com.assessment.todo.utils.hide
import com.assessment.todo.utils.show
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


@AndroidEntryPoint
class AnimeListFragment : Fragment() {
    private lateinit var binding: FragmentAnimeBinding
    private lateinit var viewModel: AnimeViewModel
    private lateinit var adapter: AnimeListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentAnimeBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(requireActivity())[AnimeViewModel::class.java]


      setRecyclerView()
      setSwipeRefresh()




        return binding.root
    }
    private fun setRecyclerView(){

        binding.rcvAnimeList.addItemDecoration(
            DividerItemDecoration(
                activity,
                DividerItemDecoration.VERTICAL
            )
        )

    }
    private fun setSwipeRefresh(){
        binding.rcvAnimeList.setItemAnimator(DefaultItemAnimator())

        binding.swipeRefresh.setOnRefreshListener {

            viewModel.removeDataFromDatabase()
            request()

            // on below line we are setting is refreshing to false.
            binding.swipeRefresh.isRefreshing = false

            Toast.makeText(requireContext(),"Data Refreshed from Remote",Toast.LENGTH_LONG).show()




        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.progressCircular.show()
        request()
        observer()
    }

    private fun request() {
        viewModel.getAnimeList()

    }

    private fun observer() {

        viewModel.mState
            .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
            .onEach { state -> handleStateChange(state) }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun handleStateChange(state: AnimeViewModel.AnimeListState) {
        when (state) {
            is AnimeViewModel.AnimeListState.Init -> Unit
            is AnimeViewModel.AnimeListState.Error -> handleError(state.rawResponse)
            is AnimeViewModel.AnimeListState.Success -> handleSuccess(state.animeList)
            is AnimeViewModel.AnimeListState.IsLoading -> handleLoading(state.isLoading)
            is AnimeViewModel.AnimeListState.Exception -> handleException(state.exception)
        }
    }

    private fun handleException(exception: Any) {
        Log.d("TAG", "handleException: $exception")
    }

    private fun handleError(response: Any) {
        Log.d("TAG", "handleException: $response")

    }

    private fun handleLoading(isLoading: Boolean) {
        Log.d("TAG", "handleException: $isLoading")

    }

    private fun handleSuccess(animeList: ArrayList<AnimeResponseModel.Data>) {

            binding.progressCircular.hide()
            adapter = AnimeListAdapter(animeList)
            binding.rcvAnimeList.adapter = adapter

        }
}