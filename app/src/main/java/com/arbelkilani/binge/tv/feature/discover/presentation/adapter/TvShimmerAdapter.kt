package com.arbelkilani.binge.tv.feature.discover.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.arbelkilani.binge.tv.R
import com.arbelkilani.binge.tv.databinding.ItemTvShowShimmerBinding
import javax.inject.Inject

class TvShimmerAdapter @Inject constructor() :
    ListAdapter<Int, TvShimmerAdapter.TvShimmerHolder>(Comparator) {

    class TvShimmerHolder(val binding: ItemTvShowShimmerBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onBindViewHolder(holder: TvShimmerHolder, position: Int) {
        //
    }

    override fun getItemCount() = 10

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = TvShimmerHolder(
        DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.item_tv_show_shimmer, parent, false
        )
    )

    companion object {
        private val Comparator = object : DiffUtil.ItemCallback<Int>() {
            override fun areItemsTheSame(oldItem: Int, newItem: Int): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Int, newItem: Int): Boolean {
                return oldItem == newItem
            }
        }
    }
}