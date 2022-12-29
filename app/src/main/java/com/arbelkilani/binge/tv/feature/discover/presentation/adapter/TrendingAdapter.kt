package com.arbelkilani.binge.tv.feature.discover.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.arbelkilani.binge.tv.R
import com.arbelkilani.binge.tv.databinding.ItemDiscoverTrendingBinding
import com.arbelkilani.binge.tv.feature.discover.domain.entities.TrendingEntity
import java.util.*
import javax.inject.Inject

class TrendingAdapter @Inject constructor(
    private val viewPager2: ViewPager2
) :
    ListAdapter<TrendingEntity, TrendingAdapter.TrendingHolder>(TrendingComparator) {

    class TrendingHolder(val binding: ItemDiscoverTrendingBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = TrendingHolder(
        DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.item_discover_trending, parent, false
        )
    )

    override fun onBindViewHolder(holder: TrendingHolder, position: Int) {
        holder.binding.tv = getItem(position)
        if (position == itemCount - 1) {
            viewPager2.post(runnable)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onCurrentListChanged(
        previousList: MutableList<TrendingEntity>,
        currentList: MutableList<TrendingEntity>
    ) {
        super.onCurrentListChanged(previousList, currentList)
        notifyDataSetChanged()
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

    private val runnable = Runnable {
        submitList(currentList + currentList)
    }
}