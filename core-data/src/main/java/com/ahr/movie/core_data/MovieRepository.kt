package com.ahr.movie.core_data

import com.ahr.movie.core_data.remote.RemoteDataSource
import com.ahr.movie.core_data.remote.response.toDomain
import com.ahr.movie.core_domain.Resource
import com.ahr.movie.core_domain.models.Movie
import com.ahr.movie.core_domain.models.MovieDetail
import com.ahr.movie.core_domain.repositories.IMovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepository @Inject constructor(private val remoteDataSource: RemoteDataSource)
    : IMovieRepository {

    override fun getAllMovies(): Flow<Resource<List<Movie>>> = flow {
        emit(Resource.Loading())

        val response = remoteDataSource.getAllMovies()
        emit(Resource.Success(response.toDomain()))
    }.catch {
        emit(Resource.Error(it.message.toString()))
    }

    override fun getFavoriteMovie(): Flow<List<Movie>> = flow {

    }

    override fun getMovieDetail(movieId: Int): Flow<Resource<MovieDetail>> = flow {
        emit(Resource.Loading())

        val response = remoteDataSource.getMovieDetail(movieId)
        emit(Resource.Success(response.toDomain()))
    }.catch {
        emit(Resource.Error(it.message.toString()))
    }

    override fun insertFavoriteMovie(movie: Movie) {

    }
}