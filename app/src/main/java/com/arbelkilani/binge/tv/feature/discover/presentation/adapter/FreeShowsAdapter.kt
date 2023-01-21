package com.arbelkilani.binge.tv.feature.discover.presentation.adapter

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.arbelkilani.binge.tv.R
import com.arbelkilani.binge.tv.databinding.ItemTvShowBackdropBinding
import com.arbelkilani.binge.tv.databinding.ItemTvShowShimmerBinding
import com.arbelkilani.binge.tv.feature.discover.presentation.listener.DiscoverItemListener
import com.arbelkilani.binge.tv.feature.discover.presentation.model.Tv
import javax.inject.Inject

class FreeShowsAdapter @Inject constructor(
    private val listener: DiscoverItemListener
) : PagingDataAdapter<Tv, RecyclerView.ViewHolder>(TvEntityComparator) {

    val width = Resources.getSystem().displayMetrics.widthPixels

    class BackdropHolder(val binding: ItemTvShowBackdropBinding) :
        RecyclerView.ViewHolder(binding.root)

    class ShimmerHolder(val binding: ItemTvShowShimmerBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        val genres = item?.genres?.joinToString(separator = DOT_SYMBOL) { it.name }
        if (holder.itemViewType == DATA_TYPE) {
            with((holder as BackdropHolder).binding) {
                root.setOnClickListener { listener.onTvClicked(item) }
                tv = item
                tvGenres.text = genres
            }
        }
    }

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
            else -> BackdropHolder(
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.item_tv_show_backdrop,
                    parent,
                    false
                )
            )
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (getItem(0)?.id == EMPTY_ITEM_ID) SHIMMER_TYPE
        else DATA_TYPE
    }

    companion object {
        private val TvEntityComparator = object : DiffUtil.ItemCallback<Tv>() {
            override fun areItemsTheSame(oldItem: Tv, newItem: Tv): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Tv, newItem: Tv): Boolean {
                return oldItem == newItem
            }
        }

        private const val EMPTY_ITEM_ID = -1
        private const val DOT_SYMBOL = " \u2022 "

        private const val SHIMMER_TYPE = 1
        private const val DATA_TYPE = 2
    }
}