package com.arbelkilani.binge.tv.feature.details.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.arbelkilani.binge.tv.R
import com.arbelkilani.binge.tv.databinding.ItemNetworkStrokeBinding
import com.arbelkilani.binge.tv.databinding.ItemSquareProviderShimmerBinding
import com.arbelkilani.binge.tv.feature.details.presentation.entities.Network
import javax.inject.Inject

class NetworksAdapter @Inject constructor() :
    ListAdapter<Network, RecyclerView.ViewHolder>(Comparator) {

    class DataHolder(val binding: ItemNetworkStrokeBinding) :
        RecyclerView.ViewHolder(binding.root)

    class ShimmerHolder(val binding: ItemSquareProviderShimmerBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            DATA_TYPE -> with((holder as DataHolder).binding) {
                network = getItem(position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            DATA_TYPE -> DataHolder(
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.item_network_stroke,
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
            object : DiffUtil.ItemCallback<Network>() {
                override fun areItemsTheSame(
                    oldItem: Network,
                    newItem: Network
                ): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(
                    oldItem: Network,
                    newItem: Network
                ): Boolean {
                    return oldItem == newItem
                }
            }

        private const val SHIMMER_TYPE = 1
        private const val DATA_TYPE = 2
    }
}