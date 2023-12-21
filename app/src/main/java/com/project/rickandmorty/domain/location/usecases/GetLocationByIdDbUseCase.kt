package com.project.rickandmorty.domain.location.usecases

import com.project.rickandmorty.data.db.entities.LocationEntity
import com.project.rickandmorty.domain.repository.RMRepository
import com.project.rickandmorty.presentation.Resource
import kotlinx.coroutines.flow.Flow

class GetLocationByIdDbUseCase(
    private val repository: RMRepository
) {
    fun execute(locationId: Int): Resource<Flow<LocationEntity>> {
        return repository.getLocationEntityDb(locationId)
    }
}