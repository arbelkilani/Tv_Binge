package com.arbelkilani.binge.tv.feature.discover.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.arbelkilani.binge.tv.R
import com.arbelkilani.binge.tv.databinding.ItemDiscoverTrendingBinding
import com.arbelkilani.binge.tv.databinding.ItemTvShowShimmerBinding
import com.arbelkilani.binge.tv.feature.discover.presentation.listener.DiscoverItemListener
import com.arbelkilani.binge.tv.feature.discover.presentation.model.Tv
import javax.inject.Inject

class TrendingAdapter @Inject constructor(
    private val listener: DiscoverItemListener
) :
    PagingDataAdapter<Tv, RecyclerView.ViewHolder>(TrendingComparator) {

    class TrendingHolder(val binding: ItemDiscoverTrendingBinding) :
        RecyclerView.ViewHolder(binding.root)

    class ShimmerHolder(val binding: ItemTvShowShimmerBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            SHIMMER_TYPE -> ShimmerHolder(
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.item_tv_show_shimmer,
                    parent,
                    false
                )
            )
            else -> TrendingHolder(
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.item_discover_trending,
                    parent,
                    false
                )
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder.itemViewType == TRENDING_TYPE) {
            with((holder as TrendingHolder).binding) {
                val item = getItem(position)
                tv = item
                tvGenres.text = item?.genres?.joinToString(separator = " \u2022 ") { it.name }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (getItem(position)?.id == -1)
            SHIMMER_TYPE else TRENDING_TYPE
    }

    companion object {
        private val TrendingComparator =
            object : DiffUtil.ItemCallback<Tv>() {
                override fun areItemsTheSame(
                    oldItem: Tv, newItem: Tv
                ): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(
                    oldItem: Tv, newItem: Tv
                ): Boolean {
                    return oldItem == newItem
                }
            }

        private const val SHIMMER_TYPE = 1
        private const val TRENDING_TYPE = 2
    }
}