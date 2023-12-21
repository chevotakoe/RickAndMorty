package com.project.rickandmorty.presentation.viewmodels.location

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.cachedIn
import androidx.paging.map
import com.project.rickandmorty.data.db.entities.LocationEntity
import com.project.rickandmorty.domain.models.mappers.toLocation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class LocationListViewModel @Inject constructor(
    pager: Pager<Int, LocationEntity>
) : ViewModel() {
    val locationPagingFlow = pager.flow.map { pagingData ->
        pagingData.map {
            it.toLocation()
        }
    }.cachedIn(viewModelScope)
}