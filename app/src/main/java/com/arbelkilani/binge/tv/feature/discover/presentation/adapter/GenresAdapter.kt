package com.arbelkilani.binge.tv.feature.discover.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.arbelkilani.binge.tv.R
import com.arbelkilani.binge.tv.common.presentation.model.Genre
import com.arbelkilani.binge.tv.databinding.ItemGenreMinBinding
import com.arbelkilani.binge.tv.databinding.ItemTagShimmerBinding
import com.arbelkilani.binge.tv.feature.discover.presentation.listener.SearchListener
import javax.inject.Inject

class GenresAdapter @Inject constructor(
    private val listener: SearchListener
) : ListAdapter<Genre, RecyclerView.ViewHolder>(GenreComparator) {

    class DataHolder(val binding: ItemGenreMinBinding) :
        RecyclerView.ViewHolder(binding.root)

    class ShimmerHolder(val binding: ItemTagShimmerBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        when (holder.itemViewType) {
            DATA_TYPE -> with((holder as DataHolder).binding) {
                root.setOnClickListener { listener.onGenreSelected(item) }
                genre = item
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            DATA_TYPE -> DataHolder(
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context), R.layout.item_genre_min, parent, false
                )
            )
            else -> ShimmerHolder(
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context), R.layout.item_tag_shimmer, parent, false
                )
            )
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (getItem(0)?.id == -1) SHIMMER_TYPE else
            DATA_TYPE
    }

    companion object {
        private val GenreComparator =
            object : DiffUtil.ItemCallback<Genre>() {
                override fun areItemsTheSame(
                    oldItem: Genre,
                    newItem: Genre
                ): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(
                    oldItem: Genre,
                    newItem: Genre
                ): Boolean {
                    return oldItem == newItem
                }
            }

        private const val SHIMMER_TYPE = 1
        private const val DATA_TYPE = 2
    }
}