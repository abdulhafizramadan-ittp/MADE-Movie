package com.ahr.movie.core_data.remote

import com.ahr.movie.core_data.remote.response.MovieItem
import com.ahr.movie.core_data.remote.service.MovieService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(private val movieService: MovieService) {

    fun getAllMovies(): Flow<ApiResponse<List<MovieItem>>> = flow {
        val response = movieService.getAllMovies("dbf6e8b675705db5ecd5d159f94a964a")
        val listMovies = response.results

        if (listMovies.isNotEmpty()) {
            emit(ApiResponse.Success(listMovies))
        } else {
            emit(ApiResponse.Empty)
        }
    }.catch {
        emit(ApiResponse.Error(it.message.toString()))
    }.flowOn(Dispatchers.IO)
}