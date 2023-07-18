package com.example.assessment.presentation.selling

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
import com.example.assessment.databinding.FragmentSellingBinding
import com.example.assessment.presentation.buying.BuyingListAdapter
import com.example.assessment.utils.hide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class SellingFragment : Fragment() {

    private lateinit var binding: FragmentSellingBinding
    private lateinit var viewModel: SellingViewModel
    private lateinit var adapter: BuyingListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSellingBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(requireActivity())[SellingViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        request()
        observer()
    }

    private fun request() {
        viewModel.getSellListFromLocal()
    }

    private fun observer() {
        viewModel.mState
            .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
            .onEach { state -> handleStateChange(state) }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun handleStateChange(state: SellingViewModel.SellingListState) {
        when (state) {
            is SellingViewModel.SellingListState.Init -> Unit
            is SellingViewModel.SellingListState.Error -> handleError(state.rawResponse)
            is SellingViewModel.SellingListState.Success -> handleSuccess(state.sellingList)
            is SellingViewModel.SellingListState.IsLoading -> handleLoading(state.isLoading)
            is SellingViewModel.SellingListState.Exception -> handleException(state.exception)
        }
    }

    private fun handleException(exception: Any) {
        Log.d("TAG", "handleException: $exception")
    }

    private fun handleError(response: Any) {

    }

    private fun handleLoading(isLoading: Boolean) {

    }

    private fun handleSuccess(sellingList: ArrayList<BuyingApiResponse>) {
        binding.progressCircularSelling.hide()
        adapter = BuyingListAdapter(sellingList)
        binding.rcvSellingList.adapter = adapter
    }
}