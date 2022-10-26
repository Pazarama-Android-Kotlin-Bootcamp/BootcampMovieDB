package com.merttoptas.botcaampmoviedb.data.remote.utils

import com.merttoptas.botcaampmoviedb.data.model.ApiError

/**
 * Created by merttoptas on 26.10.2022.
 */

sealed class DataState<T> {
    data class Success<T>(val data: T) : DataState<T>()
    data class Error<T>(val error: ApiError?) : DataState<T>()
    data class Loading<T>(val data: T? = null) : DataState<T>()
}
