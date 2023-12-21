package com.project.rickandmorty.domain.episode.usecases

import com.project.rickandmorty.presentation.Resource
import com.project.rickandmorty.domain.models.Episode
import com.project.rickandmorty.domain.repository.RMRepository

class GetFilteredEpisodesApiUseCase(
    private val repository: RMRepository
) {
    suspend fun execute(
        name: String? = null,
        episode: String? = null
    ): Resource<List<Episode>> {
        return repository.getEpisodesFiltered(
            name = name, episode = episode
        )
    }
}