package com.project.rickandmorty.domain.repository

import com.project.rickandmorty.data.db.entities.CharacterEntity
import com.project.rickandmorty.data.db.entities.EpisodeEntity
import com.project.rickandmorty.data.db.entities.LocationEntity
import com.project.rickandmorty.domain.models.Character
import com.project.rickandmorty.domain.models.Episode
import com.project.rickandmorty.domain.models.Location
import com.project.rickandmorty.presentation.Resource
import kotlinx.coroutines.flow.Flow

interface RMRepository {

    suspend fun getCharacterEntity(characterId: Int): Resource<Character>

    suspend fun getCharactersFiltered(
        name: String? = null,
        gender: String? = null,
        status: String? = null,
        species: String? = null,
        type: String? = null
    ): Resource<List<Character>>

    suspend fun getEpisodesFiltered(
        name: String? = null, episode: String? = null
    ): Resource<List<Episode>>

    suspend fun getLocationsFiltered(
        name: String? = null,
        type: String? = null,
        dimension: String? = null,
    ): Resource<List<Location>>

    fun getCharacterEntityDb(characterId: Int): Resource<Flow<CharacterEntity>>

    suspend fun getEpisodeEntity(episodeId: Int): Resource<Episode>

    fun getEpisodeEntityDb(episodeId: Int): Resource<Flow<EpisodeEntity>>

    suspend fun getLocationEntity(locationId: Int): Resource<Location>

    fun getLocationEntityDb(locationId: Int): Resource<Flow<LocationEntity>>

    fun getCharacterSearchDb(
        name: String? = null,
        status: String? = null,
        gender: String? = null,
        type: String? = null,
        species: String? = null
    ): Resource<Flow<List<CharacterEntity>>>

    fun getLocationSearchDb(
        name: String? = null,
        dimension: String? = null,
        type: String? = null
    ): Resource<Flow<List<LocationEntity>>>

    fun getEpisodeSearchDb(
        name: String? = null,
        episode: String? = null
    ): Resource<Flow<List<EpisodeEntity>>>
}