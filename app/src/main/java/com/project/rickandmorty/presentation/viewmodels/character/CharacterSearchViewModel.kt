package com.project.rickandmorty.presentation.viewmodels.character

import androidx.lifecycle.ViewModel
import com.project.rickandmorty.data.db.entities.CharacterEntity
import com.project.rickandmorty.data.repository.RMRepositoryImpl
import com.project.rickandmorty.domain.character.usecases.GetFilteredCharactersApiUseCase
import com.project.rickandmorty.domain.character.usecases.GetFilteredCharactersDbUseCase
import com.project.rickandmorty.domain.models.Character
import com.project.rickandmorty.presentation.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class CharacterSearchViewModel @Inject constructor(
    private val repository: RMRepositoryImpl
) : ViewModel() {
    suspend fun getFilteredCharacters(
        name: String? = null,
        gender: String? = null,
        status: String? = null,
        species: String? = null,
        type: String? = null
    ): Resource<List<Character>> {
        val getFilteredCharactersApiUseCase = GetFilteredCharactersApiUseCase(
            repository
        )
        return getFilteredCharactersApiUseCase.execute(
            name = name,
            gender = gender,
            status = status,
            species = species,
            type = type
        )
    }

    fun getCharacterSearchDb(
        name: String = "",
        species: String = "",
        status: String = "",
        gender: String = "",
        type: String = ""
    ): Resource<Flow<List<CharacterEntity>>> {
        val getFilteredCharactersDbUseCase = GetFilteredCharactersDbUseCase(repository)
        return getFilteredCharactersDbUseCase.execute(
            name = name,
            species = species,
            status = status,
            gender = gender,
            type = type
        )
    }

}