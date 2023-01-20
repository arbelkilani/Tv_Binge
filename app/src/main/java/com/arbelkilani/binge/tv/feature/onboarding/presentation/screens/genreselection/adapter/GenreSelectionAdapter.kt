package com.arbelkilani.binge.tv.feature.onboarding.presentation.screens.genreselection.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.arbelkilani.binge.tv.R
import com.arbelkilani.binge.tv.common.domain.entity.GenreEntity
import com.arbelkilani.binge.tv.databinding.ItemGenreBinding
import com.arbelkilani.binge.tv.feature.onboarding.presentation.screens.genreselection.listener.GenreSelectionListener
import javax.inject.Inject

class GenreSelectionAdapter @Inject constructor(
    private val genreSelectionListener: GenreSelectionListener
) : ListAdapter<GenreEntity, GenreSelectionAdapter.GenresHolder>(
    GenreComparator
) {

    class GenresHolder(val binding: ItemGenreBinding) : RecyclerView.ViewHolder(binding.root)

    private val selectedItems = mutableListOf<GenreEntity>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = GenresHolder(
        DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.item_genre, parent, false
        )
    )

    override fun onBindViewHolder(holder: GenresHolder, position: Int) {
        val current = getItem(position)
        holder.binding.genre = current
        holder.binding.root.setOnClickListener {
            it.isActivated = !it.isActivated
            genreSelectionListener.onGenreClicked(current.copy(isFavorite = it.isActivated))
        }
    }

    override fun onCurrentListChanged(
        previousList: MutableList<GenreEntity>,
        currentList: MutableList<GenreEntity>
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
        private val GenreComparator =
            object : DiffUtil.ItemCallback<GenreEntity>() {
                override fun areItemsTheSame(
                    oldItem: GenreEntity, newItem: GenreEntity
                ): Boolean {
                    return oldItem.id == newItem.id && oldItem.isFavorite == newItem.isFavorite
                }

                override fun areContentsTheSame(
                    oldItem: GenreEntity, newItem: GenreEntity
                ): Boolean {
                    return oldItem == newItem && oldItem.isFavorite == newItem.isFavorite
                }
            }
    }
}