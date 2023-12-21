package com.project.rickandmorty.presentation.viewmodels.character

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.cachedIn
import androidx.paging.map
import com.project.rickandmorty.data.db.entities.CharacterEntity
import com.project.rickandmorty.domain.models.mappers.toCharacter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class CharacterListViewModel @Inject constructor(
    pager: Pager<Int, CharacterEntity>
) : ViewModel() {

    val characterPagingFlow = pager.flow.map { pagingData ->
        pagingData.map {
            it.toCharacter()
        }
    }.cachedIn(viewModelScope)

}