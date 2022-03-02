package com.ahr.movie.util

import androidx.recyclerview.widget.DiffUtil
import com.ahr.movie.core_domain.models.Movie

class MovieDiffCallback(private val oldMovies: List<Movie>, private val newMovies: List<Movie>) : DiffUtil.Callback() {
    override fun getOldListSize(): Int =
        oldMovies.size

    override fun getNewListSize(): Int =
        newMovies.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldMovies[oldItemPosition].id == newMovies[newItemPosition].id

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldMovies[oldItemPosition] == newMovies[newItemPosition]
}