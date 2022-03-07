package com.ahr.movie.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahr.movie.core_domain.Resource
import com.ahr.movie.core_domain.models.Movie
import com.ahr.movie.core_domain.models.MovieDetail
import com.ahr.movie.core_domain.usecases.MovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val movieUseCase: MovieUseCase) : ViewModel() {

    private val _movieDetail: MutableStateFlow<Resource<MovieDetail>> =
        MutableStateFlow(Resource.Empty())
    val movieDetail get() = _movieDetail.asStateFlow()

    private val _isFavorite: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isFavorite get() = _isFavorite.asStateFlow()

    fun getMovieDetail(movieId: Int) = viewModelScope.launch {
        movieUseCase.getDetailMovie(movieId)
            .onStart {
                _movieDetail.emit(Resource.Loading())
            }
            .collect {
                _movieDetail.emit(it)
            }
    }

    fun isFavoriteMovie(movieId: Int) = viewModelScope.launch {
        movieUseCase.getFavoriteMovies(movieId)
            .collect {
                _isFavorite.emit(it != null)
            }
    }

    fun toggleFavorite(movie: Movie) = viewModelScope.launch {
        if (isFavorite.value) {
            movieUseCase.removeFavoriteMovie(movie)
        }  else {
            movieUseCase.insertFavoriteMovie(movie)
        }
    }
}