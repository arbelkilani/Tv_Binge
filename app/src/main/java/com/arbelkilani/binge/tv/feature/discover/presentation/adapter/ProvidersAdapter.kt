package com.arbelkilani.binge.tv.feature.discover.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.arbelkilani.binge.tv.R
import com.arbelkilani.binge.tv.common.domain.model.WatchProviderEntity
import com.arbelkilani.binge.tv.databinding.ItemSquareProviderBinding
import com.arbelkilani.binge.tv.databinding.ItemTagShimmerBinding
import com.arbelkilani.binge.tv.feature.discover.presentation.listener.ProviderClicked
import javax.inject.Inject

class ProvidersAdapter @Inject constructor(
    private val listener: ProviderClicked
) : ListAdapter<WatchProviderEntity, RecyclerView.ViewHolder>(WatchProviderEntityComparator) {

    class ProvidersHolder(val binding: ItemSquareProviderBinding) :
        RecyclerView.ViewHolder(binding.root)

    class ShimmerTagHolder(val binding: ItemTagShimmerBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            DEFAULT_TYPE -> with((holder as ProvidersHolder).binding) {
                provider = getItem(position)
                root.setOnClickListener {
                    listener.onProviderClicked(getItem(position))
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            DEFAULT_TYPE -> ProvidersHolder(
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.item_square_provider,
                    parent,
                    false
                )
            )
            else -> ShimmerTagHolder(
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context), R.layout.item_tag_shimmer, parent, false
                )
            )

        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (getItem(0)?.id == -1) SHIMMER_TYPE else
            DEFAULT_TYPE
    }

    companion object {
        private val WatchProviderEntityComparator =
            object : DiffUtil.ItemCallback<WatchProviderEntity>() {
                override fun areItemsTheSame(
                    oldItem: WatchProviderEntity,
                    newItem: WatchProviderEntity
                ): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(
                    oldItem: WatchProviderEntity,
                    newItem: WatchProviderEntity
                ): Boolean {
                    return oldItem == newItem
                }
            }

        private const val SHIMMER_TYPE = 1
        private const val DEFAULT_TYPE = 2
    }
}