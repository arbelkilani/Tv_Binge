package com.arbelkilani.binge.tv.feature.discover.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.arbelkilani.binge.tv.R
import com.arbelkilani.binge.tv.databinding.ItemPersonBinding
import com.arbelkilani.binge.tv.databinding.ItemShimmerCircleBinding
import com.arbelkilani.binge.tv.feature.discover.presentation.entities.Person
import com.arbelkilani.binge.tv.feature.discover.presentation.listener.DiscoverItemListener
import javax.inject.Inject

class PersonAdapter @Inject constructor(
    private val listener: DiscoverItemListener
) :
    PagingDataAdapter<Person, RecyclerView.ViewHolder>(Comparator) {

    class DataHolder(val binding: ItemPersonBinding) :
        RecyclerView.ViewHolder(binding.root)

    class ShimmerHolder(val binding: ItemShimmerCircleBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        when (holder.itemViewType) {
            DATA_TYPE -> with((holder as DataHolder).binding) {
                root.setOnClickListener { listener.onPersonClicked(item) }
                person = item
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            DATA_TYPE -> DataHolder(
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context), R.layout.item_person, parent, false
                )
            )
            else -> ShimmerHolder(
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context), R.layout.item_shimmer_circle, parent, false
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
            object : DiffUtil.ItemCallback<Person>() {
                override fun areItemsTheSame(
                    oldItem: Person,
                    newItem: Person
                ): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(
                    oldItem: Person,
                    newItem: Person
                ): Boolean {
                    return oldItem == newItem
                }
            }

        private const val SHIMMER_TYPE = 1
        private const val DATA_TYPE = 2
    }
}