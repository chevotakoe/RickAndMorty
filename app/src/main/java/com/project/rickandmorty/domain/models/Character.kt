package com.project.rickandmorty.domain.models

data class Character(
    val id: Int,
    val name: String,
    val species: String,
    val status: String,
    val gender: String,
    val image: String,
    val type: String,
    val origin: LocationDeserializationHelper,
    val location: LocationDeserializationHelper,
    val episode: List<String>
)
