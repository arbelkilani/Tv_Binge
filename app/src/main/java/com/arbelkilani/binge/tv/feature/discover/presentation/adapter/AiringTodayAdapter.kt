package com.arbelkilani.binge.tv.feature.discover.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.arbelkilani.binge.tv.R
import com.arbelkilani.binge.tv.databinding.ItemTvShowBackdropBinding
import com.arbelkilani.binge.tv.feature.discover.domain.entities.TvEntity
import javax.inject.Inject

class AiringTodayAdapter @Inject constructor() :
    PagingDataAdapter<TvEntity, AiringTodayAdapter.AiringTodayHolder>(TvEntityComparator) {

    class AiringTodayHolder(val binding: ItemTvShowBackdropBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onBindViewHolder(holder: AiringTodayHolder, position: Int) {
        holder.binding.tv = getItem(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = AiringTodayHolder(
        DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.item_tv_show_backdrop, parent, false
        )
    )

    companion object {
        private val TvEntityComparator = object : DiffUtil.ItemCallback<TvEntity>() {
            override fun areItemsTheSame(oldItem: TvEntity, newItem: TvEntity): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: TvEntity, newItem: TvEntity): Boolean {
                return oldItem == newItem
            }
        }
    }
}