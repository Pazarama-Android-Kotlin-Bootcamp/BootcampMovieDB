package com.merttoptas.botcaampmoviedb.feature.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.merttoptas.botcaampmoviedb.databinding.FragmentHomeBinding
import com.merttoptas.botcaampmoviedb.feature.home.adapter.HomePopularMovieAdapter
import com.merttoptas.botcaampmoviedb.feature.home.adapter.OnPopularMovieClickListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment(), OnPopularMovieClickListener {
    private val viewModel by viewModels<HomeViewModel>()
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launchWhenResumed {
            launch {
                viewModel.uiState.collect {
                    when (it) {
                        is HomeViewState.Success -> {
                            binding.rvPopularList.adapter =
                                HomePopularMovieAdapter(this@HomeFragment).apply {
                                    submitList(it.popularMovies)
                                }
                        }
                        is HomeViewState.Loading -> {

                        }

                    }
                }
            }

            launch {
                viewModel.uiEvent.collect {
                    when (it) {
                        is HomeViewEvent.ShowError -> {
                            Snackbar.make(
                                binding.root,
                                it.message.toString(),
                                Snackbar.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        }
    }

    override fun onMovieClick(id: Int?) {
        TODO("Not yet implemented")
    }

    override fun onFavoriteClick(id: Int?, isFavorite:Boolean) {
        viewModel.onFavoriteMovie(id, isFavorite)
    }
}