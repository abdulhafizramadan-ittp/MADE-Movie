package com.ahr.movie.core_domain.interactors

import com.ahr.movie.core_domain.Resource
import com.ahr.movie.core_domain.models.Movie
import com.ahr.movie.core_domain.models.MovieDetail
import com.ahr.movie.core_domain.repositories.IMovieRepository
import com.ahr.movie.core_domain.usecases.MovieUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieInteractor @Inject constructor(private val movieRepository: IMovieRepository) : MovieUseCase {

    override fun getAllMovies(): Flow<Resource<List<Movie>>> =
        movieRepository.getAllMovies()

    override fun getFavoriteMovies(): Flow<List<Movie>> =
        movieRepository.getFavoriteMovie()

    override fun getFavoriteMovies(movieId: Int): Flow<Movie?> =
        movieRepository.getFavoriteMovie(movieId)

    override fun getDetailMovie(movieId: Int): Flow<Resource<MovieDetail>> =
        movieRepository.getMovieDetail(movieId)

    override suspend fun insertFavoriteMovie(movie: Movie) =
        movieRepository.insertFavoriteMovie(movie)

    override suspend fun removeFavoriteMovie(movie: Movie) =
        movieRepository.removeFavoriteMovie(movie)
}