package com.project.rickandmorty.data.api.responses

import com.project.rickandmorty.domain.models.Episode

data class ListResponseEpisode(
    val results: List<Episode>
)