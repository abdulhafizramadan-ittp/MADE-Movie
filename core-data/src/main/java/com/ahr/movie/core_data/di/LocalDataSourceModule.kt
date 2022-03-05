package com.ahr.movie.core_data.di

import android.content.Context
import androidx.room.Room
import com.ahr.movie.core_data.local.daos.MovieDao
import com.ahr.movie.core_data.local.databases.MovieDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalDataSourceModule {

    @Provides
    @Singleton
    fun provideMovieDatabase(
        @ApplicationContext context: Context
    ): MovieDatabase =
        Room.databaseBuilder(
            context,
            MovieDatabase::class.java,
            "movie-database"
        ).build()

    @Provides
    @Singleton
    fun provideMovieDao(
        movieDatabase: MovieDatabase
    ): MovieDao =
        movieDatabase.movieDao()
}