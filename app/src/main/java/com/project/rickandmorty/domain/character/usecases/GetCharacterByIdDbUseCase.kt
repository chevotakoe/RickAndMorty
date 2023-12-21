package com.project.rickandmorty.domain.character.usecases

import com.project.rickandmorty.data.db.entities.CharacterEntity
import com.project.rickandmorty.domain.repository.RMRepository
import com.project.rickandmorty.presentation.Resource
import kotlinx.coroutines.flow.Flow

class GetCharacterByIdDbUseCase(
    private val repository: RMRepository
) {
    fun execute(characterId: Int): Resource<Flow<CharacterEntity>> {
        return repository.getCharacterEntityDb(characterId)
    }
}