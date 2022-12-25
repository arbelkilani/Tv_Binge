package com.arbelkilani.binge.tv.feature.onboarding.presentation.screens.providerselection.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.arbelkilani.binge.tv.R
import com.arbelkilani.binge.tv.common.domain.model.WatchProviderEntity
import com.arbelkilani.binge.tv.databinding.ItemProviderBinding
import com.arbelkilani.binge.tv.feature.onboarding.presentation.screens.providerselection.listener.ProviderSelectionListener
import javax.inject.Inject

class ProvidersAdapter @Inject constructor(
    private val providerSelectionListener: ProviderSelectionListener
) : ListAdapter<WatchProviderEntity, ProvidersAdapter.ProvidersHolder>(WatchProviderComparator) {

    class ProvidersHolder(val binding: ItemProviderBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ProvidersHolder(
        DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.item_provider, parent, false
        )
    )

    override fun onBindViewHolder(holder: ProvidersHolder, position: Int) {
        val current = getItem(position)
        with(holder.binding) {
            provider = current
            root.setOnClickListener {
                if (current.isFavorite) {
                    providerSelectionListener.removeFromFavorite(
                        holder.bindingAdapterPosition,
                        current
                    )
                } else {
                    providerSelectionListener.addToFavorite(holder.bindingAdapterPosition, current)
                }
            }
        }
    }

    override fun onCurrentListChanged(
        previousList: MutableList<WatchProviderEntity>,
        currentList: MutableList<WatchProviderEntity>
    ) {
        //super.onCurrentListChanged(previousList, currentList)
        //selectedItems.clear()
        //selectedItems.addAll(currentList.filter { it.isFavorite })
    }

    companion object {
        private val WatchProviderComparator =
            object : DiffUtil.ItemCallback<WatchProviderEntity>() {
                override fun areItemsTheSame(
                    oldItem: WatchProviderEntity, newItem: WatchProviderEntity
                ): Boolean {
                    return oldItem.id == newItem.id && oldItem.isFavorite == newItem.isFavorite
                }

                override fun areContentsTheSame(
                    oldItem: WatchProviderEntity, newItem: WatchProviderEntity
                ): Boolean {
                    return oldItem == newItem && oldItem.isFavorite == newItem.isFavorite
                }
            }
    }
}