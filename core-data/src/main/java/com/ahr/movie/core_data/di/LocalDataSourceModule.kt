package com.ahr.movie.core_data.di

import android.content.Context
import androidx.room.Room
import androidx.sqlite.db.SupportSQLiteOpenHelper
import com.ahr.movie.core_data.local.daos.MovieDao
import com.ahr.movie.core_data.local.databases.MovieDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalDataSourceModule {

    @Provides
    @Singleton
    @SQLCipherFactory
    fun provideSQLCipherFactory(): SupportSQLiteOpenHelper.Factory {
        val passphrase: ByteArray = SQLiteDatabase.getBytes("movie".toCharArray())
        return SupportFactory(passphrase)
    }

    @Provides
    @Singleton
    fun provideMovieDatabase(
        @ApplicationContext context: Context,
        @SQLCipherFactory factory: SupportSQLiteOpenHelper.Factory
    ): MovieDatabase =
        Room.databaseBuilder(
            context,
            MovieDatabase::class.java,
            "movie-database"
        )
        .openHelperFactory(factory)
        .build()

    @Provides
    @Singleton
    fun provideMovieDao(
        movieDatabase: MovieDatabase
    ): MovieDao =
        movieDatabase.movieDao()
}