package com.ahr.movie.core_data.remote.responses

import com.ahr.movie.core_domain.models.Movie
import com.google.gson.annotations.SerializedName

data class MovieItem(

	@field:SerializedName("title")
	val title: String,

	@field:SerializedName("poster_path")
	val posterPath: String,

	@field:SerializedName("id")
	val id: Int,
)

fun List<MovieItem>.toDomain(): List<Movie> = map {
	Movie(
		id = it.id,
		title = it.title,
		posterPath = it.posterPath
	)
}