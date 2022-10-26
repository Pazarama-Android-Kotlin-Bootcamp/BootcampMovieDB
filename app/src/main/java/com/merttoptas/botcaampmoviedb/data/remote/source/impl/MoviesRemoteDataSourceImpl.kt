package com.merttoptas.botcaampmoviedb.data.remote.source.impl

import com.merttoptas.botcaampmoviedb.data.model.MovieDetailResponse
import com.merttoptas.botcaampmoviedb.data.remote.api.MoviesService
import com.merttoptas.botcaampmoviedb.data.remote.source.MoviesRemoteDataSource
import com.merttoptas.botcaampmoviedb.data.remote.utils.DataState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Created by merttoptas on 26.10.2022.
 */

class MoviesRemoteDataSourceImpl @Inject constructor(private val moviesService: MoviesService) :
    BaseRemoteDataSource(), MoviesRemoteDataSource {
    override suspend fun getMovieDetail(movieId: Int): Flow<DataState<MovieDetailResponse>> {
        return getResult { moviesService.getMovieDetail(movieId) }
    }
}