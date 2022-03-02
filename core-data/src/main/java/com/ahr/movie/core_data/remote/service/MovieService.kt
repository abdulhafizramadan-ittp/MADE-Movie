package com.ahr.movie.core_data.remote.service

import com.ahr.movie.core_data.remote.response.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieService {

    @GET("discover/movie")
    suspend fun getAllMovies(
        @Query("api_key") apiKey: String
    ) : MovieResponse
}