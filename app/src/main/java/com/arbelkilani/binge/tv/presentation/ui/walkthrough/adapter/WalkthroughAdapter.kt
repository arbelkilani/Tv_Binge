package com.arbelkilani.binge.tv.presentation.ui.walkthrough.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.arbelkilani.binge.tv.R
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class WalkthroughAdapter @Inject constructor(
    @ApplicationContext private val context: Context
) : PagerAdapter() {

    private val layouts: List<Int> =
        listOf(R.layout.layout_walkthrough_1, R.layout.layout_walkthrough_2)

    override fun getCount() = layouts.size

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = LayoutInflater.from(context).inflate(layouts[position], container, false).apply {
            tag = position
        }
        container.addView(view)
        return view
    }
}