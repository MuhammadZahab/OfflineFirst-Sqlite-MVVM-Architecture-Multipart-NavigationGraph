package com.example.assessment.presentation.calling

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.assessment.data.calling.remoteApi.reponse.CallingApiResponse
import com.example.assessment.databinding.ItemCallingListBinding

class CallingListAdapter(private var list: ArrayList<CallingApiResponse>) :
    RecyclerView.Adapter<CallingListAdapter.ViewHolder>() {

    class ViewHolder(private val binding: ItemCallingListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(callingResponse: CallingApiResponse) {
            binding.calling = callingResponse
            binding.executePendingBindings()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemCallingListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

}