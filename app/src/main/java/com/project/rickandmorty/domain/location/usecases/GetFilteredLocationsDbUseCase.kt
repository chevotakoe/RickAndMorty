package com.project.rickandmorty.domain.location.usecases

import com.project.rickandmorty.data.db.entities.LocationEntity
import com.project.rickandmorty.domain.repository.RMRepository
import com.project.rickandmorty.presentation.Resource
import kotlinx.coroutines.flow.Flow

class GetFilteredLocationsDbUseCase(
    private val repository: RMRepository
) {
    fun execute(
        name: String? = null,
        type: String? = null,
        dimension: String? = null
    ): Resource<Flow<List<LocationEntity>>> {
        return repository.getLocationSearchDb(
            name = name, dimension = dimension, type = type
        )
    }
}