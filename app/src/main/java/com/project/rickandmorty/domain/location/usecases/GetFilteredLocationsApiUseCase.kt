package com.project.rickandmorty.domain.location.usecases

import com.project.rickandmorty.domain.models.Location
import com.project.rickandmorty.domain.repository.RMRepository
import com.project.rickandmorty.presentation.Resource

class GetFilteredLocationsApiUseCase(
    private val repository: RMRepository
) {
    suspend fun execute(
        name: String? = null,
        type: String? = null,
        dimension: String? = null
    ): Resource<List<Location>> {
        return repository.getLocationsFiltered(
            name = name, dimension = dimension, type = type
        )
    }
}