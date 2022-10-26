package com.merttoptas.botcaampmoviedb.data.interceptor

import com.merttoptas.botcaampmoviedb.data.remote.utils.Constants
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

/**
 * Created by merttoptas on 26.10.2022.
 */

class AuthInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request()

        val newUrl = requestBuilder.url.newBuilder()
            .addQueryParameter(Constants.KEY_API_KEY, Constants.API_KEY)
            .build()

        val newRequest = requestBuilder.newBuilder().url(newUrl).build()

        return chain.proceed(newRequest)
    }
}