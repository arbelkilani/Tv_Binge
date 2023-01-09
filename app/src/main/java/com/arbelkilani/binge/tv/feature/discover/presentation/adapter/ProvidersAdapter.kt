package com.arbelkilani.binge.tv.feature.discover.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.arbelkilani.binge.tv.R
import com.arbelkilani.binge.tv.common.domain.model.WatchProviderEntity
import com.arbelkilani.binge.tv.databinding.ItemProviderMinBinding
import com.arbelkilani.binge.tv.feature.discover.presentation.listener.ProviderClicked
import javax.inject.Inject

class ProvidersAdapter @Inject constructor(
    private val listener: ProviderClicked
) :
    ListAdapter<WatchProviderEntity, ProvidersAdapter.ProvidersHolder>(
        WatchProviderEntityComparator
    ) {

    class ProvidersHolder(val binding: ItemProviderMinBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onBindViewHolder(holder: ProvidersHolder, position: Int) {
        holder.binding.provider = getItem(position)
        holder.binding.root.setOnClickListener {
            listener.onProviderClicked(getItem(position))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ProvidersHolder(
        DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.item_provider_min, parent, false
        )
    )

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
    }
}