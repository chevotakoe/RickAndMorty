package com.project.rickandmorty.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class EpisodeEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val episode: String,
    val airDate: String,
    val characters: String
)