package com.example.assessment.presentation.buying

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.assessment.data.buying.remoteApi.reponse.BuyingApiResponse
import com.example.assessment.databinding.ItemBuyingListBinding

class BuyingListAdapter(private var list: ArrayList<BuyingApiResponse>) :
    RecyclerView.Adapter<BuyingListAdapter.ViewHolder>() {

    class ViewHolder(private val binding: ItemBuyingListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(buyingResponse: BuyingApiResponse) {
            binding.buyingResponse = buyingResponse
            binding.executePendingBindings()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemBuyingListBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

}