package com.ahr.movie.core_data.di

import com.ahr.movie.core_data.MovieRepository
import com.ahr.movie.core_domain.repositories.IMovieRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun provideMovieRepository(
        movieRepository: MovieRepository
    ) : IMovieRepository

}