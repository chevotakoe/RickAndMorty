package com.project.rickandmorty.domain.models.mappers

import com.project.rickandmorty.data.db.entities.EpisodeEntity
import com.project.rickandmorty.domain.models.Episode

fun Episode.toEpisodeEntity(): EpisodeEntity {
    val characters = characters.joinToString(separator = ",")
    return EpisodeEntity(
        id = id, name = name, episode = episode, airDate = airDate, characters = characters
    )

}

fun EpisodeEntity.toEpisode(): Episode {
    val characters = characters.split(',').toList()
    return Episode(
        id = id, name = name, episode = episode, airDate = airDate, characters = characters
    )
}