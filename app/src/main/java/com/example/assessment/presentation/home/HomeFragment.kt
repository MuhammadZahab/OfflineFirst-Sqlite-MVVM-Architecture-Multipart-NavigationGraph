package com.example.assessment.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.assessment.R
import com.example.assessment.databinding.FragmentHomeBinding
import com.example.assessment.presentation.selling.SellingViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var viewModel: SellingViewModel
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(requireActivity())[SellingViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        clickListener()
    }

    private fun clickListener() {
        binding.lblBuying.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_buyingFragment)
        }
        binding.lblSelling.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_sellingFragment)
        }
        binding.lblCalling.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_callingFragment)
        }
    }
}