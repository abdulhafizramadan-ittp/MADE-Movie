package com.ahr.movie.di

import com.ahr.movie.core_domain.usecases.MovieUseCase
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface FavoriteModuleDependencies {
    fun provideMovieUseCase(): MovieUseCase
}