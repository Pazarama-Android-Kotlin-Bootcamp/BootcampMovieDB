package com.merttoptas.botcaampmoviedb.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.merttoptas.botcaampmoviedb.data.remote.utils.Constants

/**
 * Created by merttoptas on 30.10.2022.
 */
object BindingAdapters {

    @JvmStatic
    @BindingAdapter("loadImage")
    fun loadImage(view: ImageView, url: String?) {
        Glide.with(view.context)
            .load("${Constants.BASE_IMAGE_URL}$url")
            .into(view)
    }
}