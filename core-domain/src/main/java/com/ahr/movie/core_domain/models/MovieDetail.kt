package com.ahr.movie.core_domain.models

data class MovieDetail(
	val overview: String,
	val originalLanguage: String,
	val genres: List<Genre>,
	val popularity: String,
	val voteAverage: Double,
	val id: Int,
	val title: String,
	val posterPath: String
)
