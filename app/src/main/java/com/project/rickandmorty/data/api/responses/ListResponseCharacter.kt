package com.project.rickandmorty.data.api.responses

import com.project.rickandmorty.domain.models.Character

data class ListResponseCharacter(
    val results: List<Character>
)