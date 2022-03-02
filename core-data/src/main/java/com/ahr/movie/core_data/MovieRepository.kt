package com.ahr.movie.core_data

import com.ahr.movie.core_data.remote.ApiResponse
import com.ahr.movie.core_data.remote.RemoteDataSource
import com.ahr.movie.core_data.remote.response.toDomain
import com.ahr.movie.core_domain.Resource
import com.ahr.movie.core_domain.models.Movie
import com.ahr.movie.core_domain.repositories.IMovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepository @Inject constructor(private val remoteDataSource: RemoteDataSource)
    : IMovieRepository {

    override fun getAllMovies(): Flow<Resource<List<Movie>>> = flow {
        emit(Resource.Loading())

        when (val apiResponse = remoteDataSource.getAllMovies().first()) {
            is ApiResponse.Success -> emit(
                Resource.Success(apiResponse.data.toDomain())
            )
            is ApiResponse.Error -> emit(
                Resource.Error(apiResponse.errorMessage)
            )
            is ApiResponse.Empty -> emit(
                Resource.Success(emptyList())
            )
        }
    }

    override fun getFavoriteMovie(): Flow<List<Movie>> = flow {

    }

    override fun getDetailMovie(movieId: Int): Flow<Resource<Movie>> = flow {

    }

    override fun insertFavoriteMovie(movie: Movie) {

    }
}