package com.project.rickandmorty.domain.models.mappers

import com.project.rickandmorty.data.db.entities.LocationEntity
import com.project.rickandmorty.domain.models.Location

fun Location.toLocationEntity(): LocationEntity {
    val residents = residents.joinToString(separator = ",")
    return LocationEntity(
        id = id, name = name, type = type, dimension = dimension, residents = residents
    )

}

fun LocationEntity.toLocation(): Location {
    val residents = residents.split(',').toList()
    return Location(
        id = id, name = name, type = type, dimension = dimension, residents = residents
    )
}