package com.project.rickandmorty.presentation.viewmodels.common

import androidx.lifecycle.ViewModel
import com.project.rickandmorty.data.db.entities.CharacterEntity
import com.project.rickandmorty.data.db.entities.EpisodeEntity
import com.project.rickandmorty.data.db.entities.LocationEntity
import com.project.rickandmorty.data.repository.RMRepositoryImpl
import com.project.rickandmorty.domain.character.usecases.GetCharacterByIdApiUseCase
import com.project.rickandmorty.domain.character.usecases.GetCharacterByIdDbUseCase
import com.project.rickandmorty.domain.episode.usecases.GetEpisodeByIdApiUseCase
import com.project.rickandmorty.domain.episode.usecases.GetEpisodeByIdDbUseCase
import com.project.rickandmorty.domain.location.usecases.GetLocationByIdApiUseCase
import com.project.rickandmorty.domain.location.usecases.GetLocationByIdDbUseCase
import com.project.rickandmorty.domain.models.Character
import com.project.rickandmorty.domain.models.Episode
import com.project.rickandmorty.domain.models.Location
import com.project.rickandmorty.presentation.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: RMRepositoryImpl
) : ViewModel() {

    suspend fun getCharacterDetails(characterId: Int): Resource<Character> {
        val getCharacterByIdApiUseCase = GetCharacterByIdApiUseCase(repository)
        return getCharacterByIdApiUseCase.execute(characterId)
    }

    fun getCharacterDetailsDb(characterId: Int): Resource<Flow<CharacterEntity>> {
        val getCharacterByIdDbUseCase = GetCharacterByIdDbUseCase(repository)
        return getCharacterByIdDbUseCase.execute(characterId)
    }

    suspend fun getEpisodeDetails(episodeId: Int): Resource<Episode> {
        val getEpisodeByIdApiUseCase = GetEpisodeByIdApiUseCase(repository)
        return getEpisodeByIdApiUseCase.execute(episodeId)
    }

    fun getEpisodeDetailsDb(episodeId: Int): Resource<Flow<EpisodeEntity>> {
        val getEpisodeByIdDbUseCase = GetEpisodeByIdDbUseCase(repository)
        return getEpisodeByIdDbUseCase.execute(episodeId)
    }

    suspend fun getLocationDetails(locationId: Int): Resource<Location> {
        val getLocationByIdApiUseCase = GetLocationByIdApiUseCase(repository)
        return getLocationByIdApiUseCase.execute(locationId)
    }

    fun getLocationDetailsDb(locationId: Int): Resource<Flow<LocationEntity>> {
        val getLocationByIdDbUseCase = GetLocationByIdDbUseCase(repository)
        return getLocationByIdDbUseCase.execute(locationId)
    }
}