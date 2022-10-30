package com.merttoptas.botcaampmoviedb.domain.usecase.base

import kotlinx.coroutines.flow.Flow

/**
 * Created by merttoptas on 30.10.2022.
 */

interface BaseUseCase<in Params, ReturnType : Any?> {
    fun invoke(params: Params): Flow<ReturnType>
}