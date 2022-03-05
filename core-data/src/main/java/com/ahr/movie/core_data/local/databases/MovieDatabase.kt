package com.ahr.movie.core_data.local.databases

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ahr.movie.core_data.local.daos.MovieDao
import com.ahr.movie.core_data.local.entities.MovieEntity

@Database(entities = [MovieEntity::class], version = 1)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}