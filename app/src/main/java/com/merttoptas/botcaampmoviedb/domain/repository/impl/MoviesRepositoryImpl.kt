package com.merttoptas.botcaampmoviedb.domain.repository.impl

import com.merttoptas.botcaampmoviedb.data.model.MovieDetailResponse
import com.merttoptas.botcaampmoviedb.data.remote.source.MoviesRemoteDataSource
import com.merttoptas.botcaampmoviedb.data.remote.utils.DataState
import com.merttoptas.botcaampmoviedb.domain.repository.MoviesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Created by merttoptas on 26.10.2022.
 */

class MoviesRepositoryImpl @Inject constructor(private val moviesRemoteDataSource: MoviesRemoteDataSource) :
    MoviesRepository {
    override suspend fun getMovieDetail(movieId: Int): Flow<DataState<MovieDetailResponse>> {
        return moviesRemoteDataSource.getMovieDetail(movieId)
    }
}