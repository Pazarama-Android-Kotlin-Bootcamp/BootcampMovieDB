package com.merttoptas.botcaampmoviedb.data.di

import com.merttoptas.botcaampmoviedb.data.remote.api.MoviesService
import com.merttoptas.botcaampmoviedb.data.remote.source.MoviesRemoteDataSource
import com.merttoptas.botcaampmoviedb.data.remote.source.impl.MoviesRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * Created by merttoptas on 26.10.2022.
 */

@Module
@InstallIn(SingletonComponent::class)
class MoviesModule {

    @Singleton
    @Provides
    fun provideMoviesService(retrofit: Retrofit) = retrofit.create(MoviesService::class.java)

    @Singleton
    @Provides
    fun provideMoviesRemoteDataSource(moviesService: MoviesService): MoviesRemoteDataSource =
        MoviesRemoteDataSourceImpl(moviesService)
}