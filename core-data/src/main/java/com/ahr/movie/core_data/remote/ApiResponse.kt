package com.ahr.movie.core_data.remote

sealed class ApiResponse<out R> {
    data class Success<out T>(val data: T) : ApiResponse<T>()
    data class Error<out T>(val errorMessage: String, val data: T? = null) : ApiResponse<T>()
    object Loading : ApiResponse<Nothing>()
}