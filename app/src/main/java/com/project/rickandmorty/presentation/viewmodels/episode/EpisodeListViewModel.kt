package com.project.rickandmorty.presentation.viewmodels.episode

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.cachedIn
import androidx.paging.map
import com.project.rickandmorty.data.db.entities.EpisodeEntity
import com.project.rickandmorty.domain.models.mappers.toEpisode
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class EpisodeListViewModel @Inject constructor(
    pager: Pager<Int, EpisodeEntity>
) : ViewModel() {

    val episodePagingFlow = pager.flow.map { pagingData ->
        pagingData.map {
            it.toEpisode()
        }
    }.cachedIn(viewModelScope)
}