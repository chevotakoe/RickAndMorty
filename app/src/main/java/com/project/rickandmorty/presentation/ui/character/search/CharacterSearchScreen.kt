package com.project.rickandmorty.presentation.ui.character.search

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.project.rickandmorty.data.db.entities.CharacterEntity
import com.project.rickandmorty.domain.models.Character
import com.project.rickandmorty.domain.models.mappers.toCharacter
import com.project.rickandmorty.presentation.Resource
import com.project.rickandmorty.presentation.ui.common.CharacterInfoBox
import com.project.rickandmorty.presentation.viewmodels.character.CharacterSearchViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterSearchScreen(
    name: String?,
    status: String?,
    gender: String?,
    species: String?,
    type: String?,
    navController: NavController
) {
    val viewModel = hiltViewModel<CharacterSearchViewModel>()

    val charactersState =
        produceState<Resource<List<Character>>>(initialValue = Resource.Loading()) {
            value = viewModel.getFilteredCharacters(
                name = name, gender = gender, status = status, species = species, type = type
            )
        }.value

    val characters: List<Character>? = if (charactersState is Resource.Error) {
        produceState<List<CharacterEntity>?>(initialValue = null) {
            viewModel.getCharacterSearchDb(
                name = name ?: "",
                gender = gender ?: "",
                status = status ?: "",
                species = species ?: "",
                type = type ?: ""
            ).data?.collect {
                value = it
            }
        }.value?.map { it.toCharacter() }
    } else {
        charactersState.data
    }

    Scaffold(topBar = {
        CenterAlignedTopAppBar(modifier = Modifier, title = {}, navigationIcon = {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Go Back",
                modifier = Modifier
                    .size(24.dp, 24.dp)
                    .clickable {
                        navController.popBackStack()
                    },
                tint = Color(0xFFBBAAEE)
            )
        }, colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Color.Transparent,
            titleContentColor = MaterialTheme.colorScheme.primary,
        )
        )
    }, content = {
        if (characters == null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        } else if (characters.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
            ) {
                Text(text = "No items found", textAlign = TextAlign.Center)
            }
        } else {
            LazyVerticalGrid(columns = GridCells.Fixed(2),
                verticalArrangement = Arrangement.spacedBy(space = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(space = 16.dp),
                contentPadding = PaddingValues(all = 10.dp),
                modifier = Modifier.padding(it),
                content = {
                    items(characters.size) { item ->
                        characters[item].let { character ->
                            val fieldText = mapOf(
                                Pair("name", character.name),
                                Pair("status", character.status),
                                Pair("species", character.species),
                                Pair("gender", character.gender)
                            )
                            CharacterInfoBox(image = character.image,
                                fieldText = fieldText,
                                modifier = Modifier.clickable {
                                    navController.navigate(
                                        "character_details_screen/${character.id}"
                                    )
                                })
                        }

                    }


                })
        }
    })
}

