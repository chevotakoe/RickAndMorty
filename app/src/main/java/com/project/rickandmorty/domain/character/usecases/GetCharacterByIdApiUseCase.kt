package com.project.rickandmorty.domain.character.usecases

import com.project.rickandmorty.presentation.Resource
import com.project.rickandmorty.domain.models.Character
import com.project.rickandmorty.domain.repository.RMRepository

class GetCharacterByIdApiUseCase(
    private val repository: RMRepository
) {
    suspend fun execute(characterId: Int): Resource<Character> {
        return repository.getCharacterEntity(characterId)
    }
}