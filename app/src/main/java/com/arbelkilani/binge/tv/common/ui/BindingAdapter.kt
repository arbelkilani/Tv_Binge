package com.arbelkilani.binge.tv.common.ui

import android.view.ContextThemeWrapper
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.arbelkilani.binge.tv.R
import com.bumptech.glide.Glide
import com.google.android.flexbox.FlexboxLayout

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

@BindingAdapter(value = ["app:genres"])
fun genres(view: FlexboxLayout, list: List<String>?) {
    if (list == null)
        return

    view.removeAllViews()
    for (item in list) {
        val textView = TextView(ContextThemeWrapper(view.context, R.style.Tag_Solid), null, 0)
        textView.text = item
        view.addView(textView)
    }

    view.requestLayout()
    view.invalidate()
}