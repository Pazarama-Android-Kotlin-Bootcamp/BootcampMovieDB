package com.merttoptas.botcaampmoviedb.feature.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.merttoptas.botcaampmoviedb.data.model.PopularDTO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val fireStore: FirebaseFirestore,
    private val auth: FirebaseAuth
) : ViewModel() {
    private val _uiState =
        MutableStateFlow<FavoriteViewState>(
            FavoriteViewState.Success(
                mutableListOf(),
                mutableListOf()
            )
        )
    val uiState: StateFlow<FavoriteViewState> = _uiState

    init {
        getFavoriteList()
    }

    fun getFavoriteList() {
        viewModelScope.launch {
            val id = auth.currentUser?.uid
            val docRef =
                fireStore.collection("favoriteMovie").document(id.toString()).collection("movie")
                    .get()
            docRef.addOnSuccessListener { result ->
                if (result.isEmpty.not()) {
                    val list = result.documents.map {
                        PopularDTO(
                            id = it.get("id") as Int?,
                            title = it.get("title") as String?,
                            posterPath = it.get("posterPath") as String?,
                            overview = it.get("overview") as String?,
                            isFavorite = true
                        )
                    }
                    _uiState.value =
                        FavoriteViewState.Success(list.toMutableList(), mutableListOf())
                }
            }
                .addOnFailureListener {}
        }
    }

    fun searchMovie(query: String) {
        viewModelScope.launch {
            val updateQuery = query.lowercase(Locale.getDefault())

            val currentData = (_uiState.value as FavoriteViewState.Success).data
            if (updateQuery != "") {
                currentData.let {
                    val filteredList = it.filter {
                        it.title?.lowercase(Locale.getDefault())?.contains(updateQuery) ?: false
                    }
                    _uiState.value =
                        FavoriteViewState.Success(currentData, filteredList.toMutableList())
                }
            } else {
                _uiState.value =
                    FavoriteViewState.Success(currentData, mutableListOf())
            }
        }
    }
}

sealed class FavoriteViewState {
    data class Success(
        val data: MutableList<PopularDTO>,
        val filteredData: MutableList<PopularDTO>
    ) : FavoriteViewState()

    object Loading : FavoriteViewState()
    data class Error(val message: String?) : FavoriteViewState()
}