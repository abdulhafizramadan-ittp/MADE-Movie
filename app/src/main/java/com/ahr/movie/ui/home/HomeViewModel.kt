package com.ahr.movie.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahr.movie.core_domain.Resource
import com.ahr.movie.core_domain.models.Movie
import com.ahr.movie.core_domain.usecases.MovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val movieUseCase: MovieUseCase) : ViewModel() {

    private val _movies: MutableStateFlow<Resource<List<Movie>>> = MutableStateFlow(Resource.Empty())
    val movies get() = _movies.asStateFlow()

    private val _firstLoad = MutableStateFlow(true)
    val firstLoad get() = _firstLoad.asStateFlow()

    fun getAllMovies() = viewModelScope.launch {
        movieUseCase.getAllMovies()
            .onStart {
                _movies.emit(Resource.Loading())
            }
            .collect {
                _firstLoad.emit(false)
                _movies.emit(it)
            }
    }
}