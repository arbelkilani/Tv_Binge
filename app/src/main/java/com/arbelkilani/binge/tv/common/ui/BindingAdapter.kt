package com.arbelkilani.binge.tv.common.ui

import android.view.View
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.arbelkilani.binge.tv.R
import com.bumptech.glide.Glide

@BindingAdapter(value = ["app:src"], requireAll = false)
fun bindImage(view: ImageView, url: String?) {
    Glide.with(view)
        .load(url)
        .fallback(ContextCompat.getDrawable(view.context, R.drawable.ic_splash))
        .centerCrop()
        .into(view)
}

@BindingAdapter(value = ["app:visibility"])
fun visibility(view: View, isVisible: Boolean) {
    view.isVisible = isVisible
}