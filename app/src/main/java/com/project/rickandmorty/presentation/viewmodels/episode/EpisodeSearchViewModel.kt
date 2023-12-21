package com.project.rickandmorty.presentation.viewmodels.episode

import androidx.lifecycle.ViewModel
import com.project.rickandmorty.data.db.entities.EpisodeEntity
import com.project.rickandmorty.data.repository.RMRepositoryImpl
import com.project.rickandmorty.domain.episode.usecases.GetFilteredEpisodesApiUseCase
import com.project.rickandmorty.domain.episode.usecases.GetFilteredEpisodesDbUseCase
import com.project.rickandmorty.domain.models.Episode
import com.project.rickandmorty.presentation.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class EpisodeSearchViewModel @Inject constructor(
    private val repository: RMRepositoryImpl
) : ViewModel() {
    suspend fun getFilteredEpisodes(
        name: String? = null, episode: String? = null
    ): Resource<List<Episode>> {
        val getFilteredEpisodesApiUseCase = GetFilteredEpisodesApiUseCase(repository)
        return getFilteredEpisodesApiUseCase.execute(name = name, episode = episode)
    }

    fun getEpisodeSearchDb(
        name: String = "",
        episode: String = ""
    ): Resource<Flow<List<EpisodeEntity>>> {
        val getFilteredEpisodesDbUseCase = GetFilteredEpisodesDbUseCase(repository)
        return getFilteredEpisodesDbUseCase.execute(name = name, episode = episode)
    }
}