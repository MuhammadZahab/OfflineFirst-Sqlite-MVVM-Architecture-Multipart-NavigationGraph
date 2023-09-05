package com.assessment.todo.ui.anime

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.assessment.todo.data.anime.remote.model.AnimeResponseModel
import com.assessment.todo.databinding.ItemAnimeListBinding

class AnimeListAdapter(private var list: ArrayList<AnimeResponseModel.Data>) :
    RecyclerView.Adapter<AnimeListAdapter.ViewHolder>() {

    class ViewHolder(private val binding: ItemAnimeListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(animeResponse: AnimeResponseModel.Data) {
            binding.anime = animeResponse
            binding.executePendingBindings()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemAnimeListBinding.inflate(
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