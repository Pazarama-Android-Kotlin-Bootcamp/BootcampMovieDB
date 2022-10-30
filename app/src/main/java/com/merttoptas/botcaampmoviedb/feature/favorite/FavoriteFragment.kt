package com.merttoptas.botcaampmoviedb.feature.favorite

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.merttoptas.botcaampmoviedb.data.model.PopularDTO
import com.merttoptas.botcaampmoviedb.databinding.FragmentFavoriteBinding
import com.merttoptas.botcaampmoviedb.feature.home.adapter.HomePopularMovieAdapter
import com.merttoptas.botcaampmoviedb.feature.home.adapter.OnPopularMovieClickListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavoriteFragment : Fragment(), OnPopularMovieClickListener {
    private lateinit var binding: FragmentFavoriteBinding
    private val viewModel by viewModels<FavoriteViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getFavoriteList()

        binding.toolbar.title = "Favorite Movie"
        searchMovie()
        lifecycleScope.launchWhenResumed {
            launch {
                viewModel.uiState.collect {
                    when (it) {
                        is FavoriteViewState.Success -> {
                            if (it.filteredData.isEmpty().not()) {
                                initAdapter(it.filteredData)
                            } else {
                                initAdapter(it.data)
                            }
                        }
                        is FavoriteViewState.Loading -> {

                        }
                        is FavoriteViewState.Error -> {}

                        else -> {}
                    }
                }
            }

        }

    }

    private fun initAdapter(data: MutableList<PopularDTO>) {
        binding.rvFavorite.adapter =
            HomePopularMovieAdapter(this@FavoriteFragment).apply {
                submitList(data)
            }
    }

    private fun searchMovie() {
        binding.searchView.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    if (newText.length > 4) {
                        viewModel.searchMovie(newText)
                    } else {
                        viewModel.searchMovie("")
                    }
                }
                return false
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { viewModel.searchMovie(it) }
                return false
            }
        })
    }

    override fun onMovieClick(id: Int?) {
        TODO("Not yet implemented")
    }

    override fun onFavoriteClick(popularDTO: PopularDTO) {
        TODO("Not yet implemented")
    }
}