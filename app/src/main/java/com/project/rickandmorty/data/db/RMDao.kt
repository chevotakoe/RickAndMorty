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
        "SELECT * FROM characterentity WHERE (:name IS NULL OR name LIKE '%' || :name || '%')" +
                "AND (:status IS NULL OR status = :status)" +
                "AND (:species IS NULL OR species = :species)" +
                "AND (:type IS NULL OR type = :type)" +
                "AND (:gender IS NULL OR gender = :gender)"
    )
    fun getCharactersBySearch(
        name: String? = null,
        status: String? = null,
        species: String? = null,
        type: String? = null,
        gender: String? = null
    ): Flow<List<CharacterEntity>>

    @Query("SELECT * FROM characterentity WHERE id = :characterId")
    fun getCharacterById(characterId: Int): Flow<CharacterEntity>

    @Query("SELECT * FROM locationentity")
    fun locationPagingSource(): PagingSource<Int, LocationEntity>

    @Query(
        "SELECT * FROM locationentity WHERE (:name IS NULL OR name LIKE '%' || :name || '%')" +
                "AND (:type IS NULL OR type = :type)" +
                "AND (:dimension IS NULL OR dimension LIKE '%' || :dimension || '%')"
    )
    fun getLocationsBySearch(
        name: String? = null,
        type: String? = null,
        dimension: String? = null
    ): Flow<List<LocationEntity>>

    @Query("SELECT * FROM locationentity WHERE id = :locationId")
    fun getLocationById(locationId: Int): Flow<LocationEntity>

    @Query("SELECT * FROM episodeentity")
    fun episodePagingSource(): PagingSource<Int, EpisodeEntity>

    @Query(
        "SELECT * FROM episodeentity WHERE (:name IS NULL OR name LIKE '%' || :name || '%')" +
                "AND (:episode IS NULL OR episode LIKE '%' || :episode || '%')"
    )
    fun getEpisodesBySearch(
        name: String? = null,
        episode: String? = null
    ): Flow<List<EpisodeEntity>>

    @Query("SELECT * FROM episodeentity WHERE id = :episodeId")
    fun getEpisodeById(episodeId: Int): Flow<EpisodeEntity>

}