package com.ahr.movie.core_resource.listener

import com.ahr.movie.core_domain.models.Movie

interface OnMovieClickListener {
    fun onMovieClickListener(movie: Movie)
}