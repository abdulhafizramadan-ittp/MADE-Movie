package com.ahr.movie.core_data.remote

import com.ahr.movie.core_data.remote.response.MovieDetailResponse
import com.ahr.movie.core_data.remote.response.MovieItem
import com.ahr.movie.core_data.remote.service.MovieService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(private val movieService: MovieService) {

    suspend fun getAllMovies(): List<MovieItem> = withContext(Dispatchers.IO) {
        movieService.getAllMovies(apiKey = "dbf6e8b675705db5ecd5d159f94a964a")
            .results
    }

    suspend fun getMovieDetail(movieId: Int): MovieDetailResponse = withContext(Dispatchers.IO) {
        movieService.getMovieDetail(
            movieId = movieId,
            apiKey = "dbf6e8b675705db5ecd5d159f94a964a"
        )
    }
}