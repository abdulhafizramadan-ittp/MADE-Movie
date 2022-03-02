package com.ahr.movie.core_domain.repositories

import com.ahr.movie.core_domain.Resource
import com.ahr.movie.core_domain.models.Movie
import kotlinx.coroutines.flow.Flow

interface IMovieRepository {

    fun getAllMovies(): Flow<Resource<List<Movie>>>

    fun getFavoriteMovie(): Flow<List<Movie>>

    fun getDetailMovie(movieId: Int): Flow<Resource<Movie>>

    fun insertFavoriteMovie(movie: Movie)
}