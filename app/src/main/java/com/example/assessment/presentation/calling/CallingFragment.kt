package com.example.assessment.presentation.calling

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
import com.example.assessment.data.calling.remoteApi.reponse.CallingApiResponse
import com.example.assessment.databinding.FragmentCallingBinding
import com.example.assessment.utils.hide
import com.example.assessment.utils.show
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class CallingFragment : Fragment() {
    private lateinit var binding: FragmentCallingBinding
    private lateinit var viewModel: CallingViewModel
    private lateinit var adapter: CallingListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentCallingBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(requireActivity())[CallingViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.progressCircular.show()
        request()
        observer()
    }

    private fun request() {
        viewModel.getCallingList()
    }

    private fun observer() {
        viewModel.mState
            .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
            .onEach { state -> handleStateChange(state) }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun handleStateChange(state: CallingViewModel.CallingListState) {
        when (state) {
            is CallingViewModel.CallingListState.Init -> Unit
            is CallingViewModel.CallingListState.Error -> handleError(state.rawResponse)
            is CallingViewModel.CallingListState.Success -> handleSuccess(state.callingList)
            is CallingViewModel.CallingListState.IsLoading -> handleLoading(state.isLoading)
            is CallingViewModel.CallingListState.Exception -> handleException(state.exception)
        }
    }

    private fun handleException(exception: Any) {
        Log.d("TAG", "handleException: $exception")
    }

    private fun handleError(response: Any) {

    }

    private fun handleLoading(isLoading: Boolean) {

    }

    private fun handleSuccess(callingList: ArrayList<CallingApiResponse>) {
        binding.progressCircular.hide()
        adapter = CallingListAdapter(callingList)
        binding.rcvCallingList.adapter = adapter
    }
}