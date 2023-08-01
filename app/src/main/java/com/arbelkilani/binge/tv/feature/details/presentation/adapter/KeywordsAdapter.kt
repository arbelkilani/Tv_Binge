package com.arbelkilani.binge.tv.feature.details.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.arbelkilani.binge.tv.R
import com.arbelkilani.binge.tv.databinding.ItemKeywordBinding
import com.arbelkilani.binge.tv.feature.details.presentation.entities.Keywords
import javax.inject.Inject

class KeywordsAdapter @Inject constructor() :
    ListAdapter<Keywords, KeywordsAdapter.DataHolder>(Comparator) {

    class DataHolder(val binding: ItemKeywordBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onBindViewHolder(holder: DataHolder, position: Int) {
        holder.binding.keyword = getItem(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = DataHolder(
        DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_keyword,
            parent,
            false
        )
    )

    companion object {
        private val Comparator =
            object : DiffUtil.ItemCallback<Keywords>() {
                override fun areItemsTheSame(
                    oldItem: Keywords,
                    newItem: Keywords
                ): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(
                    oldItem: Keywords,
                    newItem: Keywords
                ): Boolean {
                    return oldItem == newItem
                }
            }
    }
}