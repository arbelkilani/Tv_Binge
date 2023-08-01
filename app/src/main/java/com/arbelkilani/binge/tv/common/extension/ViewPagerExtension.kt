package com.arbelkilani.binge.tv.common.extension

import android.content.res.Resources
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import kotlin.math.abs

private const val SCALE_FACTOR = .8F
private const val PADDING_FACTOR = .1F
private const val MARGIN_FACTOR = .05F

fun ViewPager2.removeOverScroll() {
    (getChildAt(0) as? RecyclerView)?.overScrollMode = RecyclerView.OVER_SCROLL_NEVER
}

fun ViewPager2.scalePagerTransformer() {
    val width = Resources.getSystem().displayMetrics.widthPixels
    val paddingFactor = (PADDING_FACTOR * width).toInt()
    offscreenPageLimit = 3
    setPadding(paddingFactor, 0, paddingFactor, 0)
    val compositePageTransformer = CompositePageTransformer()
    compositePageTransformer.addTransformer(MarginPageTransformer((MARGIN_FACTOR * width).toInt()))
    compositePageTransformer.addTransformer { page, position ->
        val r = 1 - abs(position)
        page.scaleY = (SCALE_FACTOR + r * (1 - SCALE_FACTOR))
    }
    setPageTransformer(compositePageTransformer)
}