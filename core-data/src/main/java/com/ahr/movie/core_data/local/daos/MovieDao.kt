package com.ahr.movie.core_data.local.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ahr.movie.core_data.local.entities.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Query("SELECT * FROM movieentity")
    fun getAllFavoriteMovie(): Flow<List<MovieEntity>>

    @Query("SELECT * FROM movieentity WHERE id = :movieId")
    fun getFavoriteMovie(movieId: Int): Flow<MovieEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovieToFavorite(movieEntity: MovieEntity)

    @Query("DELETE FROM movieentity WHERE id = :movieId")
    fun removeMovieFromFavorite(movieId: Int)

}