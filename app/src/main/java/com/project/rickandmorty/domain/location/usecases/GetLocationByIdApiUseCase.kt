package com.project.rickandmorty.domain.location.usecases

import com.project.rickandmorty.presentation.Resource
import com.project.rickandmorty.domain.models.Location
import com.project.rickandmorty.domain.repository.RMRepository

class GetLocationByIdApiUseCase (
    private val repository: RMRepository
) {
    suspend fun execute(locationId: Int): Resource<Location> {
        return repository.getLocationEntity(locationId)
    }
}