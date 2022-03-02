package com.ahr.movie.core_resource.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ahr.movie.core_domain.models.Movie
import com.ahr.movie.core_resource.R
import com.ahr.movie.core_resource.databinding.ItemMovieBinding
import com.ahr.movie.core_resource.diffutil.MovieDiffCallback
import com.bumptech.glide.Glide
import javax.inject.Inject

class MovieAdapter @Inject constructor() : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    inner class MovieViewHolder(private val binding: ItemMovieBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie) {
            binding.apply {
                tvMovieTitle.text = movie.title
                Glide.with(itemView.context)
                    .load("http://image.tmdb.org/t/p/w500/${movie.posterPath}")
                    .placeholder(R.drawable.bg_loading_image)
                    .error(R.drawable.bg_broken_image)
                    .into(ivMovie)
            }
        }
    }

    private val movies = arrayListOf<Movie>()

    fun setMovies(movies: List<Movie>?) {
        if (movies != null) {
            val diffCallback = MovieDiffCallback(this.movies, movies)
            val diffResult = DiffUtil.calculateDiff(diffCallback)
            this.movies.addAll(movies)
            diffResult.dispatchUpdatesTo(this)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemMovieBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movies[position])
    }

    override fun getItemCount(): Int =
        movies.size
}