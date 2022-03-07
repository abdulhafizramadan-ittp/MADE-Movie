package com.ahr.movie.feature_favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahr.movie.core_domain.models.Movie
import com.ahr.movie.core_domain.usecases.MovieUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FavoriteViewModel(private val movieUseCase: MovieUseCase) : ViewModel() {

    private val _favoriteMovies: MutableStateFlow<List<Movie>> = MutableStateFlow(emptyList())
    val favoriteMovies = _favoriteMovies.asStateFlow()

    private val _firstLoad = MutableStateFlow(true)
    val firstLoad get() = _firstLoad.asStateFlow()

    fun getFavoriteMovies() = viewModelScope.launch {
        movieUseCase.getFavoriteMovies()
            .collect {
                _firstLoad.emit(false)
                _favoriteMovies.emit(it)
            }
    }
}