package com.arbelkilani.binge.tv.presentation.ui.onboarding.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.arbelkilani.binge.tv.R
import com.arbelkilani.binge.tv.databinding.ItemProviderBinding
import com.arbelkilani.binge.tv.domain.entities.WatchProviderEntity
import javax.inject.Inject

class ProvidersAdapter @Inject constructor() :
    ListAdapter<WatchProviderEntity, ProvidersAdapter.ProvidersHolder>(WatchProviderComparator) {

    class ProvidersHolder(val binding: ItemProviderBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ProvidersHolder(
        DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.item_provider, parent, false
        )
    )

    override fun onBindViewHolder(holder: ProvidersHolder, position: Int) {
        holder.binding.provider = getItem(position)
    }

    companion object {
        private val WatchProviderComparator =
            object : DiffUtil.ItemCallback<WatchProviderEntity>() {
                override fun areItemsTheSame(
                    oldItem: WatchProviderEntity, newItem: WatchProviderEntity
                ): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(
                    oldItem: WatchProviderEntity, newItem: WatchProviderEntity
                ): Boolean {
                    return oldItem == newItem
                }
            }
    }
}