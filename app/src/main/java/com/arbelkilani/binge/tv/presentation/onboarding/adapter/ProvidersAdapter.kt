package com.arbelkilani.binge.tv.presentation.onboarding.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.arbelkilani.binge.tv.R
import com.arbelkilani.binge.tv.databinding.ItemProviderBinding
import com.arbelkilani.binge.tv.domain.entities.WatchProviderEntity
import com.arbelkilani.binge.tv.presentation.onboarding.listener.WatchProviderListener
import javax.inject.Inject

class ProvidersAdapter @Inject constructor(
    private val watchProviderListener: WatchProviderListener
) :
    ListAdapter<WatchProviderEntity, ProvidersAdapter.ProvidersHolder>(WatchProviderComparator) {

    class ProvidersHolder(val binding: ItemProviderBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ProvidersHolder(
        DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.item_provider, parent, false
        )
    )

    override fun onBindViewHolder(holder: ProvidersHolder, position: Int) {
        val current = getItem(position)
        with(holder.binding) {
            this.rbSelect.isSelected = true
            provider = current
            root.setOnClickListener {
                mask.isVisible = !mask.isVisible
                watchProviderListener.onWatchProviderClicked(
                    getItem(position).copy(
                        isFavorite = mask.isVisible
                    ), mask.isVisible
                )
            }
        }
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