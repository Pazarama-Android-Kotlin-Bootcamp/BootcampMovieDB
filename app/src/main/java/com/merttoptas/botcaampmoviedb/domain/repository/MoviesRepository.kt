package com.merttoptas.botcaampmoviedb.domain.repository

import com.merttoptas.botcaampmoviedb.data.model.MovieDetailResponse
import com.merttoptas.botcaampmoviedb.data.model.PopularResponse
import com.merttoptas.botcaampmoviedb.data.remote.utils.DataState
import kotlinx.coroutines.flow.Flow

/**
 * Created by merttoptas on 26.10.2022.
 */

interface MoviesRepository {
    suspend fun getMovieDetail(movieId: Int): Flow<DataState<MovieDetailResponse>>
    suspend fun getPopularMovies(page:Int): Flow<DataState<PopularResponse>>
}