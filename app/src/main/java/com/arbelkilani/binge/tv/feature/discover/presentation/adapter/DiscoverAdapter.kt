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
import com.arbelkilani.binge.tv.databinding.ItemTvShowPosterBinding
import com.arbelkilani.binge.tv.databinding.ItemTvShowShimmerBinding
import com.arbelkilani.binge.tv.feature.discover.domain.entities.TvEntity
import com.arbelkilani.binge.tv.feature.discover.presentation.listener.DiscoverItemListener
import javax.inject.Inject

class DiscoverAdapter @Inject constructor(
    private val listener: DiscoverItemListener
) :
    PagingDataAdapter<TvEntity, RecyclerView.ViewHolder>(TvEntityComparator) {

    val width = Resources.getSystem().displayMetrics.widthPixels

    class BackdropHolder(val binding: ItemTvShowBackdropBinding) :
        RecyclerView.ViewHolder(binding.root)

    class ShimmerHolder(val binding: ItemTvShowShimmerBinding) :
        RecyclerView.ViewHolder(binding.root)

    class PosterHolder(val binding: ItemTvShowPosterBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        val genres = item?.genres?.joinToString(separator = DOT_SYMBOL) { it.name }
        when (holder.itemViewType) {
            BACKDROP_TYPE -> with((holder as BackdropHolder).binding) {
                tv = item
                tvGenres.text = genres
            }
            POSTER_TYPE -> with((holder as PosterHolder).binding) {
                root.layoutParams.width = width / 4
                tv = item
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
            POSTER_TYPE -> {
                PosterHolder(
                    DataBindingUtil.inflate(
                        LayoutInflater.from(parent.context),
                        R.layout.item_tv_show_poster,
                        parent,
                        false
                    )
                )
            }
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
        return if (getItem(0)?.id == EMPTY_ITEM_ID)
            SHIMMER_TYPE else {
            if (getItem(position)?.backdrop?.endsWith("null") == true) POSTER_TYPE else BACKDROP_TYPE
        }
    }

    companion object {
        private val TvEntityComparator = object : DiffUtil.ItemCallback<TvEntity>() {
            override fun areItemsTheSame(oldItem: TvEntity, newItem: TvEntity): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: TvEntity, newItem: TvEntity): Boolean {
                return oldItem == newItem
            }
        }

        private const val EMPTY_ITEM_ID = -1
        private const val DOT_SYMBOL = " \u2022 "

        private const val SHIMMER_TYPE = 1
        private const val BACKDROP_TYPE = 2
        private const val POSTER_TYPE = 3
    }
}