package com.ahr.movie.di

import com.ahr.movie.core_domain.interactors.MovieInteractor
import com.ahr.movie.core_domain.usecases.MovieUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    @Singleton
    abstract fun provideMovieUseCase(
        movieInteractor: MovieInteractor
    ): MovieUseCase

}