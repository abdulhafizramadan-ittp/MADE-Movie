package com.ahr.movie.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahr.movie.core_domain.Resource
import com.ahr.movie.core_domain.models.Movie
import com.ahr.movie.core_domain.usecases.MovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val movieUseCase: MovieUseCase) : ViewModel() {

    private val _movies: MutableStateFlow<Resource<List<Movie>>> = MutableStateFlow(Resource.Empty())
    val movies: StateFlow<Resource<List<Movie>>>
        get() = _movies

    fun getAllMovies() = viewModelScope.launch {
        movieUseCase.getAllMovies()
            .onStart {
                _movies.emit(Resource.Loading())
            }
            .collect {
                _movies.emit(it)
            }
    }
}