package com.ahr.movie.core_data

import com.ahr.movie.core_data.local.LocalDataSource
import com.ahr.movie.core_data.local.entities.toDomain
import com.ahr.movie.core_data.local.entities.toEntity
import com.ahr.movie.core_data.remote.RemoteDataSource
import com.ahr.movie.core_data.remote.responses.toDomain
import com.ahr.movie.core_domain.Resource
import com.ahr.movie.core_domain.models.Movie
import com.ahr.movie.core_domain.models.MovieDetail
import com.ahr.movie.core_domain.repositories.IMovieRepository
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : IMovieRepository {

    override fun getAllMovies(): Flow<Resource<List<Movie>>> = flow {
        emit(Resource.Loading())

        val response = remoteDataSource.getAllMovies()
        emit(Resource.Success(response.toDomain()))
    }.catch {
        emit(Resource.Error(it.message.toString()))
    }

    override fun getFavoriteMovie(): Flow<List<Movie>> = flow {
        localDataSource.getAllFavoriteMovie()
            .collect { favoriteMovies ->
                emit(favoriteMovies.toDomain())
            }
    }

    override fun getFavoriteMovie(movieId: Int): Flow<Movie?> = flow {
        localDataSource.getFavoriteMovie(movieId)
            .collect {
                emit(it?.toDomain())
            }
    }

    override fun getMovieDetail(movieId: Int): Flow<Resource<MovieDetail>> = flow {
        emit(Resource.Loading())

        val response = remoteDataSource.getMovieDetail(movieId)
        emit(Resource.Success(response.toDomain()))
    }.catch {
        emit(Resource.Error(it.message.toString()))
    }

    override suspend fun insertFavoriteMovie(movie: Movie) =
        localDataSource.insertMovieToFavorite(movie.toEntity())

    override suspend fun removeFavoriteMovie(movie: Movie) =
        localDataSource.removeMovieFromFavorite(movie.id)
}