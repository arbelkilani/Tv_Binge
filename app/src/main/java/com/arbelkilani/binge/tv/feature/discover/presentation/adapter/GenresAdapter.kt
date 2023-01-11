package com.arbelkilani.binge.tv.feature.discover.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.arbelkilani.binge.tv.R
import com.arbelkilani.binge.tv.common.domain.model.GenreEntity
import com.arbelkilani.binge.tv.databinding.ItemGenreMinBinding
import com.arbelkilani.binge.tv.databinding.ItemTagShimmerBinding
import com.arbelkilani.binge.tv.feature.discover.presentation.listener.DiscoverItemListener
import javax.inject.Inject

class GenresAdapter @Inject constructor(
    private val listener: DiscoverItemListener
) :
    ListAdapter<GenreEntity, RecyclerView.ViewHolder>(GenreComparator) {

    class GenresHolder(val binding: ItemGenreMinBinding) :
        RecyclerView.ViewHolder(binding.root)

    class ShimmerTagHolder(val binding: ItemTagShimmerBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            DEFAULT_TYPE -> with((holder as GenresHolder).binding) {
                genre = getItem(position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            DEFAULT_TYPE -> GenresHolder(
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context), R.layout.item_genre_min, parent, false
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
        private val GenreComparator =
            object : DiffUtil.ItemCallback<GenreEntity>() {
                override fun areItemsTheSame(
                    oldItem: GenreEntity,
                    newItem: GenreEntity
                ): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(
                    oldItem: GenreEntity,
                    newItem: GenreEntity
                ): Boolean {
                    return oldItem == newItem
                }
            }

        private const val SHIMMER_TYPE = 1
        private const val DEFAULT_TYPE = 2
    }
}