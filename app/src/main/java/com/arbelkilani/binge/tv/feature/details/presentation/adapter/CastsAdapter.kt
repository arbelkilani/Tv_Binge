package com.arbelkilani.binge.tv.feature.details.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.arbelkilani.binge.tv.R
import com.arbelkilani.binge.tv.databinding.ItemCastBinding
import com.arbelkilani.binge.tv.databinding.ItemSquareProviderShimmerBinding
import com.arbelkilani.binge.tv.feature.details.presentation.entities.Cast
import javax.inject.Inject

class CastsAdapter @Inject constructor() :
    ListAdapter<Cast, RecyclerView.ViewHolder>(Comparator) {

    class DataHolder(val binding: ItemCastBinding) :
        RecyclerView.ViewHolder(binding.root)

    class ShimmerHolder(val binding: ItemSquareProviderShimmerBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            DATA_TYPE -> with((holder as DataHolder).binding) {
                cast = getItem(position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            DATA_TYPE -> DataHolder(
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.item_cast,
                    parent,
                    false
                )
            )
            else -> ShimmerHolder(
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.item_square_provider_shimmer,
                    parent,
                    false
                )
            )

        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (getItem(0)?.id == -1) SHIMMER_TYPE else
            DATA_TYPE
    }

    companion object {
        private val Comparator =
            object : DiffUtil.ItemCallback<Cast>() {
                override fun areItemsTheSame(
                    oldItem: Cast,
                    newItem: Cast
                ): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(
                    oldItem: Cast,
                    newItem: Cast
                ): Boolean {
                    return oldItem == newItem
                }
            }

        private const val SHIMMER_TYPE = 1
        private const val DATA_TYPE = 2
    }
}