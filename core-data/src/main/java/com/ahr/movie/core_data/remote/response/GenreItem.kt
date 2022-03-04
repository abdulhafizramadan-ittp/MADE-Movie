package com.ahr.movie.core_data.remote.response

import com.ahr.movie.core_domain.models.Genre
import com.google.gson.annotations.SerializedName

data class GenreItem(

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("id")
    val id: Int
)

fun List<GenreItem>.toDomain(): List<Genre> =
    map {
        Genre(
            name = it.name,
            id = it.id
        )
    }