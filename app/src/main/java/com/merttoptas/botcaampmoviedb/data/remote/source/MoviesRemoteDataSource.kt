package com.merttoptas.botcaampmoviedb.data.remote.source

import com.merttoptas.botcaampmoviedb.data.model.MovieDetailResponse
import com.merttoptas.botcaampmoviedb.data.model.PopularResponse
import com.merttoptas.botcaampmoviedb.data.remote.utils.DataState
import kotlinx.coroutines.flow.Flow

/**
 * Created by merttoptas on 26.10.2022.
 */

interface MoviesRemoteDataSource {
    suspend fun getMovieDetail(movieId: Int): Flow<DataState<MovieDetailResponse>>
    suspend fun getPopularMovies(page: Int): Flow<DataState<PopularResponse>>
}