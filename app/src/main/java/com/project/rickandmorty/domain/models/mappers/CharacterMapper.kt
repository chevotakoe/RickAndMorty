package com.project.rickandmorty.domain.models.mappers

import com.project.rickandmorty.data.db.entities.CharacterEntity
import com.project.rickandmorty.domain.models.Character
import com.project.rickandmorty.domain.models.LocationDeserializationHelper

fun Character.toCharacterEntity(): CharacterEntity {
    val episodes = episode.joinToString(separator = ",")
    val originUrl = if (origin.url == "") {
        "https://rickandmortyapi.com/api/location/0"
    } else {
        origin.url
    }
    val locationUrl = if (location.url == "") {
        "https://rickandmortyapi.com/api/location/0"
    } else {
        location.url
    }
    return CharacterEntity(
        id = id,
        name = name,
        species = species,
        status = status,
        gender = gender,
        image = image,
        locationId = locationUrl.substringAfterLast('/').toInt(),
        locationName = location.name,
        episode = episodes,
        type = type,
        originId = originUrl.substringAfterLast('/').toInt(),
        originName = origin.name
    )

}

fun CharacterEntity.toCharacter(): Character {
    val episodes = episode.split(',').toList()
    return Character(
        id = id,
        name = name,
        species = species,
        status = status,
        gender = gender,
        image = image,
        episode = episodes,
        type = type,
        origin = LocationDeserializationHelper(
            name = originName, url = "https://rickandmortyapi.com/api/location/$originId"
        ),
        location = LocationDeserializationHelper(
            name = locationName, url = "https://rickandmortyapi.com/api/location/$locationId"
        ),

        )
}