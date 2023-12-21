package com.project.rickandmorty.presentation.viewmodels.location

import androidx.lifecycle.ViewModel
import com.project.rickandmorty.data.db.entities.LocationEntity
import com.project.rickandmorty.data.repository.RMRepositoryImpl
import com.project.rickandmorty.domain.location.usecases.GetFilteredLocationsApiUseCase
import com.project.rickandmorty.domain.location.usecases.GetFilteredLocationsDbUseCase
import com.project.rickandmorty.domain.models.Location
import com.project.rickandmorty.presentation.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class LocationSearchViewModel @Inject constructor(
    private val repository: RMRepositoryImpl
) : ViewModel() {
    suspend fun getFilteredLocations(
        name: String? = null, type: String? = null, dimension: String? = null
    ): Resource<List<Location>> {
        val getFilteredLocationsApiUseCase = GetFilteredLocationsApiUseCase(repository)
        return getFilteredLocationsApiUseCase.execute(
            name = name, type = type, dimension = dimension
        )
    }

    fun getLocationSearchDb(
        name: String = "",
        type: String = "",
        dimension: String = ""
    ): Resource<Flow<List<LocationEntity>>> {
        val getFilteredLocationsDbUseCase = GetFilteredLocationsDbUseCase(repository)
        return getFilteredLocationsDbUseCase.execute(
            name = name,
            type = type,
            dimension = dimension
        )
    }
}