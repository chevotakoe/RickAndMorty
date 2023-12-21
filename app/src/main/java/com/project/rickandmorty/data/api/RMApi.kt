package com.project.rickandmorty.data.api

import com.project.rickandmorty.data.api.responses.ListResponseCharacter
import com.project.rickandmorty.data.api.responses.ListResponseEpisode
import com.project.rickandmorty.data.api.responses.ListResponseLocation
import com.project.rickandmorty.domain.models.Character
import com.project.rickandmorty.domain.models.Episode
import com.project.rickandmorty.domain.models.Location
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RMApi {

    companion object {
        const val BASE_URL = "https://rickandmortyapi.com/api/"
    }

    @GET("character")
    suspend fun getCharactersList(
        @Query("page") page: Int? = null,
        @Query("name") name: String? = null,
        @Query("gender") gender: String? = null,
        @Query("status") status: String? = null,
        @Query("species") species: String? = null,
        @Query("type") type: String? = null
    ): ListResponseCharacter

    @GET("location")
    suspend fun getLocationsList(
        @Query("page") page: Int? = null,
        @Query("name") name: String? = null,
        @Query("type") type: String? = null,
        @Query("dimension") dimension: String? = null
    ): ListResponseLocation

    @GET("episode")
    suspend fun getEpisodesList(
        @Query("page") page: Int? = null,
        @Query("name") name: String? = null,
        @Query("episode") episode: String? = null
    ): ListResponseEpisode

    @GET("character/{id}")
    suspend fun getCharacterById(
        @Path("id") id: Int
    ): Character

    @GET("location/{id}")
    suspend fun getLocationById(
        @Path("id") id: Int
    ): Location

    @GET("episode/{id}")
    suspend fun getEpisodeById(
        @Path("id") id: Int
    ): Episode
}