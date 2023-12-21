package com.project.rickandmorty.domain.character.usecases

import com.project.rickandmorty.domain.models.Character
import com.project.rickandmorty.domain.repository.RMRepository
import com.project.rickandmorty.presentation.Resource

class GetFilteredCharactersApiUseCase(
    private val repository: RMRepository
) {
    suspend fun execute(
        name: String? = null,
        gender: String? = null,
        status: String? = null,
        species: String? = null,
        type: String? = null
    ): Resource<List<Character>> {
        return repository.getCharactersFiltered(
            name = name, gender = gender, status = status, species = species, type = type
        )
    }
}