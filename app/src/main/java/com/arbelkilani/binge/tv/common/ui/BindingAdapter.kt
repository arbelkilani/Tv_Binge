package com.arbelkilani.binge.tv.common.ui

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
        .centerCrop()
        .into(view)
}