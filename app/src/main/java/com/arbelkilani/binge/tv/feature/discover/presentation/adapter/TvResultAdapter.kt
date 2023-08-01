package com.arbelkilani.binge.tv.feature.discover.presentation.adapter

import android.content.res.Resources
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.arbelkilani.binge.tv.R
import com.arbelkilani.binge.tv.databinding.ItemTvBackdropBinding
import com.arbelkilani.binge.tv.databinding.ItemTvShowShimmerBinding
import com.arbelkilani.binge.tv.feature.home.presentation.model.Tv
import com.bumptech.glide.Glide
import javax.inject.Inject

class TvResultAdapter @Inject constructor() :
    PagingDataAdapter<Tv, RecyclerView.ViewHolder>(Comparator) {

    val width = Resources.getSystem().displayMetrics.widthPixels

    class DataHolder(val binding: ItemTvBackdropBinding) :
        RecyclerView.ViewHolder(binding.root)

    class ShimmerHolder(val binding: ItemTvShowShimmerBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        Log.i("TAG**", "onBindViewHolder in TvResultAdapter : $item")
        if (holder.itemViewType == DATA_TYPE) {
            with((holder as DataHolder).binding) {
                tvGenres.visibility = View.GONE
                tvTitle.isVisible = false
                vMask.isVisible = false
                Glide.with(root.context)
                    .load(item?.backdrop)
                    .into(ivImage)
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
            else -> DataHolder(
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.item_tv_backdrop,
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
        private val Comparator = object : DiffUtil.ItemCallback<Tv>() {
            override fun areItemsTheSame(oldItem: Tv, newItem: Tv): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Tv, newItem: Tv): Boolean {
                return oldItem == newItem
            }
        }

        private const val EMPTY_ITEM_ID = -1
        private const val SHIMMER_TYPE = 1
        private const val DATA_TYPE = 2
    }
}