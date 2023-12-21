package com.project.rickandmorty.domain.episode.usecases

import com.project.rickandmorty.presentation.Resource
import com.project.rickandmorty.domain.models.Episode
import com.project.rickandmorty.domain.repository.RMRepository

class GetEpisodeByIdApiUseCase (
    private val repository: RMRepository
) {
    suspend fun execute(episodeId: Int): Resource<Episode> {
        return repository.getEpisodeEntity(episodeId)
    }
}