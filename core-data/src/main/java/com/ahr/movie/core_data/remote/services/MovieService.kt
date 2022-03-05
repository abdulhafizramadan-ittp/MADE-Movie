package com.ahr.movie.core_data.remote.services

import com.ahr.movie.core_data.remote.responses.MovieDetailResponse
import com.ahr.movie.core_data.remote.responses.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {

    @GET("discover/movie")
    suspend fun getAllMovies(
        @Query("api_key") apiKey: String
    ) : MovieResponse

    @GET("movie/{movieId}")
    suspend fun getMovieDetail(
        @Path("movieId") movieId: Int,
        @Query("api_key") apiKey: String
    ): MovieDetailResponse
}