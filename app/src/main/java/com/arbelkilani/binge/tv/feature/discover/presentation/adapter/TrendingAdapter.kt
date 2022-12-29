package com.arbelkilani.binge.tv.feature.discover.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.arbelkilani.binge.tv.R
import com.arbelkilani.binge.tv.databinding.ItemDiscoverTrendingBinding
import com.arbelkilani.binge.tv.feature.discover.domain.entities.TvEntity
import javax.inject.Inject

class TrendingAdapter @Inject constructor() :
    ListAdapter<TvEntity, TrendingAdapter.TrendingHolder>(TrendingComparator) {

    class TrendingHolder(val binding: ItemDiscoverTrendingBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = TrendingHolder(
        DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.item_discover_trending, parent, false
        )
    )

    override fun onBindViewHolder(holder: TrendingHolder, position: Int) {
        holder.binding.tv = getItem(position)
    }

    companion object {
        private val TrendingComparator =
            object : DiffUtil.ItemCallback<TvEntity>() {
                override fun areItemsTheSame(
                    oldItem: TvEntity, newItem: TvEntity
                ): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(
                    oldItem: TvEntity, newItem: TvEntity
                ): Boolean {
                    return oldItem == newItem
                }
            }
    }
}