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
import javax.inject.Inject

class GenresAdapter @Inject constructor() :
    ListAdapter<GenreEntity, GenresAdapter.GenresHolder>(
        GenreComparator
    ) {

    class GenresHolder(val binding: ItemGenreMinBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onBindViewHolder(holder: GenresHolder, position: Int) {
        holder.binding.genre = getItem(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = GenresHolder(
        DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.item_genre_min, parent, false
        )
    )

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
    }
}