package com.merttoptas.botcaampmoviedb.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.merttoptas.botcaampmoviedb.data.model.Popular
import com.merttoptas.botcaampmoviedb.data.remote.utils.DataState
import com.merttoptas.botcaampmoviedb.domain.repository.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val moviesRepository: MoviesRepository) :
    ViewModel() {
    private val _uiState = MutableStateFlow<HomeViewState>(HomeViewState.Success(mutableListOf()))
    val uiState: StateFlow<HomeViewState> = _uiState

    private val _uiEvent = MutableSharedFlow<HomeViewEvent>(replay = 0)
    val uiEvent: SharedFlow<HomeViewEvent> = _uiEvent

    init {
        getPopularMovies()
    }

    private fun getPopularMovies() {
        viewModelScope.launch {
            moviesRepository.getPopularMovies(1).collect {
                when (it) {
                    is DataState.Success -> {
                        _uiState.value = HomeViewState.Success(it.data.results?.toMutableList())
                    }
                    is DataState.Error -> {
                        _uiEvent.emit(HomeViewEvent.ShowError(it.error?.status_message))
                    }
                    is DataState.Loading -> {
                        _uiState.value = HomeViewState.Loading
                    }
                }

            }
        }
    }
}

sealed class HomeViewEvent {
    data class ShowError(val message: String?) : HomeViewEvent()
}

sealed class HomeViewState {
    class Success(val popularMovies: MutableList<Popular?>?) : HomeViewState()
    object Loading : HomeViewState()
}