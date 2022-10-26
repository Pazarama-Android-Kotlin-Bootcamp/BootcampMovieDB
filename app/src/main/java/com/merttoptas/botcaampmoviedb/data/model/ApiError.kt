package com.merttoptas.botcaampmoviedb.data.model

import com.google.gson.annotations.SerializedName

/**
 * Created by merttoptas on 26.10.2022.
 */

data class ApiError(
    @SerializedName("status_code")
    val status_code: Long,
    @SerializedName("status_message")
    val status_message: String
)