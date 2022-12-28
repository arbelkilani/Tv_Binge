package com.arbelkilani.binge.tv.feature.discover.presentation.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.arbelkilani.binge.tv.R
import com.arbelkilani.binge.tv.databinding.ItemDiscoverTrendingBinding
import com.arbelkilani.binge.tv.feature.discover.domain.entities.TrendingEntity
import javax.inject.Inject

class TrendingAdapter @Inject constructor() :
    ListAdapter<TrendingEntity, TrendingAdapter.TrendingHolder>(TrendingComparator) {

    class TrendingHolder(val binding: ItemDiscoverTrendingBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = TrendingHolder(
        DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.item_discover_trending, parent, false
        )
    )

    override fun onBindViewHolder(holder: TrendingHolder, position: Int) {
        Log.i("TAG**", "item = ${getItem(position)}")
        holder.binding.tv = getItem(position)
    }

    companion object {
        private val TrendingComparator =
            object : DiffUtil.ItemCallback<TrendingEntity>() {
                override fun areItemsTheSame(
                    oldItem: TrendingEntity, newItem: TrendingEntity
                ): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(
                    oldItem: TrendingEntity, newItem: TrendingEntity
                ): Boolean {
                    return oldItem == newItem
                }
            }
    }
}