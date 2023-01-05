package com.arbelkilani.binge.tv.feature.discover.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.arbelkilani.binge.tv.R
import com.arbelkilani.binge.tv.databinding.ItemTvShowBinding
import com.arbelkilani.binge.tv.feature.discover.domain.entities.TvEntity
import javax.inject.Inject

class DiscoverAdapter @Inject constructor() :
    PagingDataAdapter<TvEntity, DiscoverAdapter.DiscoverHolder>(TvEntityComparator) {

    class DiscoverHolder(val binding: ItemTvShowBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onBindViewHolder(holder: DiscoverHolder, position: Int) {
        with(holder.binding) {
            val item = getItem(position)
            tv = item
            tvGenres.text = item?.genres?.joinToString(separator = " \u2022 ") { it.name }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = DiscoverHolder(
        DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.item_tv_show, parent, false
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