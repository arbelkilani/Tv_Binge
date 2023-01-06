package com.arbelkilani.binge.tv.common.ui

import android.view.View
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.arbelkilani.binge.tv.R
import com.bumptech.glide.Glide

@BindingAdapter(value = ["app:src"], requireAll = false)
fun bindImage(view: ImageView, url: String?) {
    Glide.with(view)
        .load(url)
        .fallback(ContextCompat.getDrawable(view.context, R.drawable.ic_splash))
        .into(view)
}

@BindingAdapter(value = ["app:activated"])
fun activated(view: View, isActivated: Boolean) {
    view.isActivated = isActivated
}