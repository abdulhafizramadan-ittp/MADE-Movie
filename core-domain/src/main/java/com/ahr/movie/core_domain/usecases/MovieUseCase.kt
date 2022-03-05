package com.ahr.movie.core_domain.usecases

import com.ahr.movie.core_domain.Resource
import com.ahr.movie.core_domain.models.Movie
import com.ahr.movie.core_domain.models.MovieDetail
import kotlinx.coroutines.flow.Flow

interface MovieUseCase {

    fun getAllMovies(): Flow<Resource<List<Movie>>>

    fun getFavoriteMovie(): Flow<List<Movie>>

    fun getDetailMovie(movieId: Int): Flow<Resource<MovieDetail>>

    fun getFavoriteMovie(movieId: Int): Flow<Movie?>

    suspend fun insertFavoriteMovie(movie: Movie)

    suspend fun removeFavoriteMovie(movie: Movie)
}