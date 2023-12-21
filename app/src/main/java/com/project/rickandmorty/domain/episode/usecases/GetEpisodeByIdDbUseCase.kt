package com.project.rickandmorty.domain.episode.usecases

import com.project.rickandmorty.presentation.Resource
import com.project.rickandmorty.data.db.entities.EpisodeEntity
import com.project.rickandmorty.domain.repository.RMRepository
import kotlinx.coroutines.flow.Flow

class GetEpisodeByIdDbUseCase (
    private val repository: RMRepository
) {
    fun execute(episodeId: Int): Resource<Flow<EpisodeEntity>> {
        return repository.getEpisodeEntityDb(episodeId)
    }
}