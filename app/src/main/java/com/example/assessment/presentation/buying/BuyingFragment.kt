package com.example.assessment.presentation.buying

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.example.assessment.data.buying.remoteApi.reponse.BuyingApiResponse
import com.example.assessment.databinding.FragmentBuyingBinding
import com.example.assessment.utils.hide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class BuyingFragment : Fragment() {
    private lateinit var binding: FragmentBuyingBinding
    private lateinit var viewModel: BuyingViewModel
    private lateinit var adapter: BuyingListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentBuyingBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(requireActivity())[BuyingViewModel::class.java]

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        request()
        observer()
    }

    private fun request() {
        viewModel.getBuyingList()
    }

    private fun observer() {
        viewModel.mState
            .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
            .onEach { state -> handleStateChange(state) }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun handleStateChange(state: BuyingViewModel.BuyingListState) {
        when (state) {
            is BuyingViewModel.BuyingListState.Init -> Unit
            is BuyingViewModel.BuyingListState.Error -> handleError(state.rawResponse)
            is BuyingViewModel.BuyingListState.Success -> handleSuccess(state.buyingList)
            is BuyingViewModel.BuyingListState.IsLoading -> handleLoading(state.isLoading)
            is BuyingViewModel.BuyingListState.Exception -> handleException(state.exception)
        }
    }

    private fun handleException(exception: Any) {
        Log.d("TAG", "handleException: $exception")
    }

    private fun handleError(response: Any) {

    }

    private fun handleLoading(isLoading: Boolean) {

    }

    private fun handleSuccess(buyingList: ArrayList<BuyingApiResponse>) {
        binding.progressCircularBuying.hide()
        adapter = BuyingListAdapter(buyingList)
        binding.rcvBuyingList.adapter = adapter
    }
}