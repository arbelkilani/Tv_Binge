package com.arbelkilani.binge.tv.feature.discover.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.arbelkilani.binge.tv.R
import com.arbelkilani.binge.tv.databinding.ItemTvShowShimmerBinding
import com.arbelkilani.binge.tv.feature.discover.presentation.model.Tv
import javax.inject.Inject

class DiscoverShimmerAdapter @Inject constructor() :
    ListAdapter<Tv, DiscoverShimmerAdapter.ShimmerHolder>(Comparator) {

    class ShimmerHolder(val binding: ItemTvShowShimmerBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onBindViewHolder(holder: DiscoverShimmerAdapter.ShimmerHolder, position: Int) {
        val item = getItem(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ShimmerHolder(
        DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.item_tv_show_shimmer, parent, false
        )
    )

    companion object {
        private val Comparator = object : DiffUtil.ItemCallback<Tv>() {
            override fun areItemsTheSame(oldItem: Tv, newItem: Tv): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Tv, newItem: Tv): Boolean {
                return oldItem == newItem
            }
        }
    }
}