package com.ahr.movie.core_data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ahr.movie.core_domain.models.Movie

@Entity
data class MovieEntity(
	val title: String,
	@ColumnInfo(name = "post_path")
	val posterPath: String,
	@PrimaryKey
	val id: Int,
)

fun List<MovieEntity>.toDomain(): List<Movie> = map {
	Movie(
		id = it.id,
		title = it.title,
		posterPath = it.posterPath
	)
}

fun Movie.toEntity(): MovieEntity =
	MovieEntity(
		title = title,
		posterPath = posterPath,
		id = id
	)

fun MovieEntity.toDomain(): Movie =
	Movie(
		title = title,
		posterPath = posterPath,
		id = id
	)