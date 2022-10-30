package com.merttoptas.botcaampmoviedb.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.merttoptas.botcaampmoviedb.data.model.PopularDTO
import com.merttoptas.botcaampmoviedb.data.remote.utils.DataState
import com.merttoptas.botcaampmoviedb.domain.repository.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val moviesRepository: MoviesRepository,
    private val firebaseAuth: FirebaseAuth,
    private val fireStore: FirebaseFirestore
) :
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
                        _uiState.value = HomeViewState.Success(it.data.results?.map {
                            val data = getFavoriteList(it?.id).first()

                            PopularDTO(
                                id = it?.id,
                                title = it?.title,
                                posterPath = it?.posterPath,
                                overview = it?.overview,
                                isFavorite = data?.find { c -> c == it?.id.toString() } != null
                            )
                        }?.toMutableList())
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

    private fun getFavoriteList(id: Int?): Flow<MutableList<String>?> = channelFlow {
        val favoriteList = mutableListOf<String>()
        val callBack =
            fireStore.collection("favoriteMovie").document(firebaseAuth.currentUser?.uid.toString())
                .collection("movie").document(id.toString()).get().addOnSuccessListener {
                    it.data?.values?.forEach { data ->
                        favoriteList.add(data.toString())
                    }
                    trySendBlocking(favoriteList)

                }.addOnFailureListener {
                    trySendBlocking(mutableListOf())
                }
        awaitClose { callBack.isCanceled() }
    }

    fun onFavoriteMovie(id: Int?, isFavorite: Boolean) {
        viewModelScope.launch {
            val userId = firebaseAuth.currentUser?.uid
            if (isFavorite) {
                deleteMovie(userId.toString(), id)
            } else {
                insertMovie(userId.toString(), id)
            }

        }
    }

    private fun insertMovie(userId: String, id: Int?) {
        fireStore.collection("favoriteMovie").document(userId.toString()).collection("movie")
            .let { ref ->
                ref.document("$id")
                    .set(mapOf("movieId" to id))

                    .addOnSuccessListener { documentReference ->
                        viewModelScope.launch {
                            _uiState.value =
                                HomeViewState.Success((_uiState.value as HomeViewState.Success).popularMovies?.map { safeList ->
                                    if (safeList?.id == id) {
                                        safeList.isFavorite = true
                                    }
                                    safeList
                                }?.toMutableList())

                            _uiEvent.emit(HomeViewEvent.ShowError("Movie added to favorite"))

                        }
                    }
                    .addOnFailureListener { error ->
                        viewModelScope.launch {
                            _uiEvent.emit(HomeViewEvent.ShowError(error.message.toString()))

                        }
                    }
            }
    }

    private fun deleteMovie(userId: String, id: Int?) {
        fireStore.collection("favoriteMovie").document(userId.toString()).collection("movie")
            .let { ref ->
                ref.document("$id")
                    .delete()
                    .addOnSuccessListener {
                        viewModelScope.launch {
                            _uiState.value =
                                HomeViewState.Success((_uiState.value as HomeViewState.Success).popularMovies?.map { safeList ->
                                    if (safeList?.id == id) {
                                        safeList.isFavorite = false
                                    }
                                    safeList
                                }?.toMutableList())

                            _uiEvent.emit(HomeViewEvent.ShowError("Movie deleted from favorite"))

                        }
                    }
                    .addOnFailureListener { error ->
                        viewModelScope.launch {
                            _uiEvent.emit(HomeViewEvent.ShowError(error.message.toString()))

                        }
                    }
            }
    }
}

sealed class HomeViewEvent {
    data class ShowError(val message: String?) : HomeViewEvent()
}

sealed class HomeViewState {
    class Success(val popularMovies: MutableList<PopularDTO>?) : HomeViewState()
    object Loading : HomeViewState()
}