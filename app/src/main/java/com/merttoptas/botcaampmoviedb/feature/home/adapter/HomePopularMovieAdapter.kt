package com.merttoptas.botcaampmoviedb.feature.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.merttoptas.botcaampmoviedb.data.model.Popular
import com.merttoptas.botcaampmoviedb.databinding.ItemPopularMovieLayoutBinding

/**
 * Created by merttoptas on 30.10.2022.
 */

class HomePopularMovieAdapter(private val listener: OnPopularMovieClickListener) :
    ListAdapter<Popular, HomePopularMovieAdapter.PopularMovieViewHolder>(PopularMovieDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularMovieViewHolder {
        return PopularMovieViewHolder(
            ItemPopularMovieLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: PopularMovieViewHolder, position: Int) {
        holder.bind(getItem(position), listener)
    }

    class PopularMovieViewHolder(private val binding: ItemPopularMovieLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Popular, listener: OnPopularMovieClickListener) {
            binding.dataHolder = data
            binding.listener = listener
            binding.executePendingBindings()
        }
    }

    class PopularMovieDiffUtil : DiffUtil.ItemCallback<Popular>() {
        override fun areItemsTheSame(oldItem: Popular, newItem: Popular): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Popular, newItem: Popular): Boolean {
            return oldItem == newItem
        }
    }
}

interface OnPopularMovieClickListener {
    fun onMovieClick(id: String)
    fun onFavoriteClick(id: String)
}
