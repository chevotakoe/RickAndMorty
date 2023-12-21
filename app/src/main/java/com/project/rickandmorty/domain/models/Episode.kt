package com.project.rickandmorty.domain.models

import com.google.gson.annotations.SerializedName

data class Episode(
    val id: Int,
    val name: String,
    val episode: String,
    @SerializedName("air_date")
    val airDate: String,
    val characters: List<String>
)
