package com.arbelkilani.binge.tv.feature.onboarding.presentation.screens.providerselection.adapter

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

    private val selectedItems = mutableListOf<WatchProviderEntity>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ProvidersHolder(
        DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.item_provider, parent, false
        )
    )

    override fun onBindViewHolder(holder: ProvidersHolder, position: Int) {
        val current = getItem(position)

        holder.binding.mask.isVisible = current in selectedItems
        with(holder.binding) {
            provider = current
            root.setOnClickListener {
                mask.isVisible = !mask.isVisible
                if (mask.isVisible) selectedItems.add(current)
                else selectedItems.remove(current)
                providerSelectionListener.onWatchProviderClicked(current.copy(isFavorite = mask.isVisible))
            }
        }
    }

    override fun onCurrentListChanged(
        previousList: MutableList<WatchProviderEntity>,
        currentList: MutableList<WatchProviderEntity>
    ) {
        super.onCurrentListChanged(previousList, currentList)
        selectedItems.clear()
        selectedItems.addAll(currentList.filter { it.isFavorite })
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
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