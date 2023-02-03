package com.arbelkilani.binge.tv.feature.discover.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.arbelkilani.binge.tv.R
import com.arbelkilani.binge.tv.common.presentation.model.Genre
import com.arbelkilani.binge.tv.databinding.ItemGenreMinBinding
import com.arbelkilani.binge.tv.feature.discover.presentation.listener.SearchListener
import javax.inject.Inject

class GenresAdapter @Inject constructor(
    private val listener: SearchListener
) : PagingDataAdapter<Genre, GenresAdapter.DataHolder>(Comparator) {

    class DataHolder(val binding: ItemGenreMinBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onBindViewHolder(holder: DataHolder, position: Int) {
        val item = getItem(position)
        with(holder.binding.chip) {
            isCheckable = true
            text = item?.name
            isChecked = item?.isSelected == true
            setOnCheckedChangeListener { _, boolean ->
                listener.onGenreSelected(item?.copy(isSelected = boolean))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        DataHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context), R.layout.item_genre_min, parent, false
            )
        )

    companion object {
        private val Comparator =
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
    }
}