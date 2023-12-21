package com.project.rickandmorty.domain.character.usecases

import com.project.rickandmorty.presentation.Resource
import com.project.rickandmorty.data.db.entities.CharacterEntity
import com.project.rickandmorty.domain.repository.RMRepository
import kotlinx.coroutines.flow.Flow

class GetFilteredCharactersDbUseCase(
    private val repository: RMRepository
) {
    fun execute(
        name: String? = null,
        species: String? = null,
        status: String? = null,
        gender: String? = null,
        type: String? = null
    ): Resource<Flow<List<CharacterEntity>>> {
        return repository.getCharacterSearchDb(
            name = name,
            gender = gender,
            status = status,
            species = species,
            type = type
        )
    }
}