package com.arbelkilani.binge.tv.feature.discover.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.viewpager.widget.PagerAdapter
import com.arbelkilani.binge.tv.R
import com.arbelkilani.binge.tv.databinding.ItemDiscoverTrendingBinding
import com.arbelkilani.binge.tv.feature.discover.domain.entities.TvEntity
import javax.inject.Inject

class TestAdapter @Inject constructor(
    private val data: List<TvEntity>
) : PagerAdapter() {

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun getCount(): Int {
        return Int.MAX_VALUE
    }

    private fun getRealCount(): Int {
        return data.size
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val virtualPosition = position % getRealCount()
        val binding = DataBindingUtil.inflate<ItemDiscoverTrendingBinding>(
            LayoutInflater.from(container.context),
            R.layout.item_discover_trending,
            container,
            false
        )

        binding.tv = data[virtualPosition]

        container.addView(binding.root)
        return binding.root
    }
}