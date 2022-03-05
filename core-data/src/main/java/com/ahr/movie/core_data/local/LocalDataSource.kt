package com.ahr.movie.core_data.local

import com.ahr.movie.core_data.local.daos.MovieDao
import com.ahr.movie.core_data.local.entities.MovieEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LocalDataSource @Inject constructor(private val movieDao: MovieDao) {

    suspend fun getAllFavoriteMovie(): Flow<List<MovieEntity>> =
        withContext(Dispatchers.IO) {
            movieDao.getAllFavoriteMovie()
        }

    suspend fun getFavoriteMovie(movieId: Int): Flow<MovieEntity?> =
        withContext(Dispatchers.IO) {
            movieDao.getFavoriteMovie(movieId)
        }

    suspend fun insertMovieToFavorite(movieEntity: MovieEntity) =
        withContext(Dispatchers.IO) {
            movieDao.insertMovieToFavorite(movieEntity)
        }

    suspend fun removeMovieFromFavorite(movieId: Int) =
        withContext(Dispatchers.IO) {
            movieDao.removeMovieFromFavorite(movieId)
        }
}