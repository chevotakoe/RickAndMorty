package com.project.rickandmorty.domain.episode.usecases

import com.project.rickandmorty.data.db.entities.EpisodeEntity
import com.project.rickandmorty.domain.repository.RMRepository
import com.project.rickandmorty.presentation.Resource
import kotlinx.coroutines.flow.Flow

class GetFilteredEpisodesDbUseCase(
    private val repository: RMRepository
) {
    fun execute(
        name: String = "",
        episode: String = ""
    ): Resource<Flow<List<EpisodeEntity>>> {
        return repository.getEpisodeSearchDb(
            name = name, episode = episode
        )
    }
}