package com.project.rickandmorty.data.repository

import com.project.rickandmorty.data.api.RMApi
import com.project.rickandmorty.data.db.RMDatabase
import com.project.rickandmorty.data.db.entities.CharacterEntity
import com.project.rickandmorty.data.db.entities.EpisodeEntity
import com.project.rickandmorty.data.db.entities.LocationEntity
import com.project.rickandmorty.domain.models.Character
import com.project.rickandmorty.domain.models.Episode
import com.project.rickandmorty.domain.models.Location
import com.project.rickandmorty.domain.repository.RMRepository
import com.project.rickandmorty.presentation.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RMRepositoryImpl @Inject constructor(
    private val api: RMApi, private val db: RMDatabase
) : RMRepository {
    override suspend fun getCharacterEntity(characterId: Int): Resource<Character> {
        val response = try {
            api.getCharacterById(characterId)
        } catch (e: Exception) {
            return Resource.Error("Error occurred.")
        }
        return Resource.Success(response)
    }

    override suspend fun getCharactersFiltered(
        name: String?,
        gender: String?,
        status: String?,
        species: String?,
        type: String?
    ): Resource<List<Character>> {
        val response = try {
            api.getCharactersList(
                name = name, gender = gender, status = status, species = species, type = type
            ).results
        } catch (e: Exception) {
            return Resource.Error("Error occurred.")
        }
        return Resource.Success(response)
    }

    override suspend fun getEpisodesFiltered(
        name: String?, episode: String?
    ): Resource<List<Episode>> {
        val response = try {
            api.getEpisodesList(
                name = name, episode = episode
            ).results
        } catch (e: Exception) {
            return Resource.Error("Error occurred.")
        }
        return Resource.Success(response)
    }

    override suspend fun getLocationsFiltered(
        name: String?,
        type: String?,
        dimension: String?
    ): Resource<List<Location>> {
        val response = try {
            api.getLocationsList(
                name = name, type = type, dimension = dimension
            ).results
        } catch (e: Exception) {
            return Resource.Error("Error occurred.")
        }
        return Resource.Success(response)
    }

    override fun getCharacterEntityDb(characterId: Int): Resource<Flow<CharacterEntity>> {
        val response = try {
            db.dao.getCharacterById(characterId)
        } catch (e: Exception) {
            return Resource.Error("Error occurred")
        }
        return Resource.Success(response)
    }

    override suspend fun getEpisodeEntity(episodeId: Int): Resource<Episode> {
        val response = try {
            api.getEpisodeById(episodeId)
        } catch (e: Exception) {
            return Resource.Error("Error occurred.")
        }
        return Resource.Success(response)
    }

    override fun getEpisodeEntityDb(episodeId: Int): Resource<Flow<EpisodeEntity>> {
        val response = try {
            db.dao.getEpisodeById(episodeId)
        } catch (e: Exception) {
            return Resource.Error("Error occurred")
        }
        return Resource.Success(response)
    }

    override suspend fun getLocationEntity(locationId: Int): Resource<Location> {
        val response = try {
            api.getLocationById(locationId)
        } catch (e: Exception) {
            return Resource.Error("Error occurred.")
        }
        return Resource.Success(response)
    }

    override fun getLocationEntityDb(locationId: Int): Resource<Flow<LocationEntity>> {
        val response = try {
            db.dao.getLocationById(locationId)
        } catch (e: Exception) {
            return Resource.Error("Error occurred")
        }
        return Resource.Success(response)
    }

    override fun getCharacterSearchDb(
        name: String?,
        status: String?,
        gender: String?,
        type: String?,
        species: String?
    ): Resource<Flow<List<CharacterEntity>>> {
        val response = try {
            db.dao.getCharactersBySearch(
                name = name,
                status = status,
                species = species,
                type = type,
                gender = gender
            )
        } catch (e: Exception) {
            return Resource.Error("Error occurred")
        }
        return Resource.Success(response)
    }

    override fun getLocationSearchDb(
        name: String?,
        dimension: String?,
        type: String?
    ): Resource<Flow<List<LocationEntity>>> {
        val response = try {
            db.dao.getLocationsBySearch(name = name, dimension = dimension, type = type)
        } catch (e: Exception) {
            return Resource.Error("Error occurred")
        }
        return Resource.Success(response)
    }

    override fun getEpisodeSearchDb(
        name: String?,
        episode: String?
    ): Resource<Flow<List<EpisodeEntity>>> {
        val response = try {
            db.dao.getEpisodesBySearch(name = name, episode = episode)
        } catch (e: Exception) {
            return Resource.Error("Error occurred")
        }
        return Resource.Success(response)
    }
}