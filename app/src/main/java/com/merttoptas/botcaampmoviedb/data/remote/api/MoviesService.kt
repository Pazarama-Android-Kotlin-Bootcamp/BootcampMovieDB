package com.merttoptas.botcaampmoviedb.data.remote.api

import com.merttoptas.botcaampmoviedb.data.model.*
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by merttoptas on 26.10.2022.
 */

interface MoviesService {
    @GET("movie/popular")
    suspend fun getPopularMovies(): Response<PopularResponse>

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(): Response<TopRatedResponse>

    @GET("movie/upcoming")
    suspend fun getUpComingMovies(): Response<UpComingResponse>

    @GET("movie/{movie_id}")
    suspend fun getMovieDetail(@Path("movie_id") movieId: Int): Response<MovieDetailResponse>

}