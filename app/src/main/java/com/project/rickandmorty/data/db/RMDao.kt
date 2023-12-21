package com.project.rickandmorty.data.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.project.rickandmorty.data.db.entities.CharacterEntity
import com.project.rickandmorty.data.db.entities.EpisodeEntity
import com.project.rickandmorty.data.db.entities.LocationEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RMDao {

    @Upsert
    suspend fun upsertAllCharacters(characters: List<CharacterEntity>)

    @Upsert
    suspend fun upsertAllLocations(locations: List<LocationEntity>)

    @Upsert
    suspend fun upsertAllEpisodes(episodes: List<EpisodeEntity>)

    @Query("DELETE FROM characterentity")
    suspend fun clearAllCharacters()

    @Query("DELETE FROM locationentity")
    suspend fun clearAllLocations()

    @Query("DELETE FROM episodeentity")
    suspend fun clearAllEpisodes()

    @Query("SELECT * FROM characterentity")
    fun characterPagingSource(): PagingSource<Int, CharacterEntity>

    @Query(
        "SELECT * FROM characterentity WHERE (:name IS '' OR name LIKE '%' || :name || '%')" +
                "AND (:type IS '' OR type = :type)" +
                "AND (:status IS '' OR status = :status)" +
                "AND (:species IS '' OR species = :species)" +
                "AND (:gender IS '' OR gender = :gender)"
    )
    fun getCharactersBySearch(
        name: String,
        status: String,
        species: String,
        type: String,
        gender: String
    ): Flow<List<CharacterEntity>>

    @Query("SELECT * FROM characterentity WHERE id = :characterId")
    fun getCharacterById(characterId: Int): Flow<CharacterEntity>

    @Query("SELECT * FROM locationentity")
    fun locationPagingSource(): PagingSource<Int, LocationEntity>

    @Query(
        "SELECT * FROM locationentity WHERE (:name IS '' OR name LIKE ('%' || :name || '%'))" +
                "AND (:type IS '' OR type = :type)" +
                "AND (:dimension IS '' OR dimension LIKE ('%' || :dimension || '%'))"
    )
    fun getLocationsBySearch(
        name: String,
        type: String,
        dimension: String
    ): Flow<List<LocationEntity>>

    @Query("SELECT * FROM locationentity WHERE id = :locationId")
    fun getLocationById(locationId: Int): Flow<LocationEntity>

    @Query("SELECT * FROM episodeentity")
    fun episodePagingSource(): PagingSource<Int, EpisodeEntity>

    @Query(
        "SELECT * FROM episodeentity WHERE (:name IS '' OR name LIKE '%' || :name || '%')" +
                "AND (:episode IS '' OR episode LIKE '%' || :episode || '%')"
    )
    fun getEpisodesBySearch(
        name: String,
        episode: String
    ): Flow<List<EpisodeEntity>>

    @Query("SELECT * FROM episodeentity WHERE id = :episodeId")
    fun getEpisodeById(episodeId: Int): Flow<EpisodeEntity>

}