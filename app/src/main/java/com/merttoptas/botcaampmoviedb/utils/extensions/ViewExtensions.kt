package com.merttoptas.botcaampmoviedb.utils.extensions

import android.view.View

/**
 * Created by merttoptas on 29.10.2022.
 */

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}

fun View.changeVisibility(isVisible: Boolean) {
    if (isVisible) {
        visible()
    } else {
        gone()
    }
}