package com.project.rickandmorty.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CharacterEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val species: String,
    val status: String,
    val type: String,
    val gender: String,
    val image: String,
    val originName: String,
    val originId: Int,
    val locationId: Int,
    val locationName: String,
    val episode: String
)