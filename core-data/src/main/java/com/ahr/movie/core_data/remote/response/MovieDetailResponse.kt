package com.ahr.movie.core_data.remote.response

import com.ahr.movie.core_domain.models.MovieDetail
import com.google.gson.annotations.SerializedName

data class MovieDetailResponse(

	@field:SerializedName("overview")
	val overview: String,

	@field:SerializedName("original_language")
	val originalLanguage: String,

	@field:SerializedName("genres")
	val genres: List<GenreItem>,

	@field:SerializedName("popularity")
	val popularity: String,

	@field:SerializedName("vote_average")
	val voteAverage: Double,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("title")
	val title: String,

	@field:SerializedName("poster_path")
	val posterPath: String
)

fun MovieDetailResponse.toDomain(): MovieDetail =
	MovieDetail(
		overview = overview,
		originalLanguage = originalLanguage,
		genres = genres.toDomain(),
		popularity = popularity,
		voteAverage = voteAverage,
		id = id,
		title = title,
		posterPath = posterPath
	)
