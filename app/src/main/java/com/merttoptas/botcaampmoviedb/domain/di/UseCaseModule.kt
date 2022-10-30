package com.merttoptas.botcaampmoviedb.domain.di

import com.google.firebase.auth.FirebaseAuth
import com.merttoptas.botcaampmoviedb.domain.usecase.login.LoginUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

/**
 * Created by merttoptas on 30.10.2022.
 */

@Module
@InstallIn(ViewModelComponent::class)
class UseCaseModule {

    @ViewModelScoped
    @Provides
    fun provideLoginUseCase(firebaseAuth: FirebaseAuth) = LoginUseCase(firebaseAuth)
}