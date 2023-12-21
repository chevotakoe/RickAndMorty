package com.project.rickandmorty.presentation.ui.location.details

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
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
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.project.rickandmorty.data.db.entities.CharacterEntity
import com.project.rickandmorty.data.db.entities.LocationEntity
import com.project.rickandmorty.domain.models.Character
import com.project.rickandmorty.domain.models.Location
import com.project.rickandmorty.domain.models.mappers.toCharacter
import com.project.rickandmorty.domain.models.mappers.toLocation
import com.project.rickandmorty.presentation.Resource
import com.project.rickandmorty.presentation.ui.common.NestedCharactersGrid
import com.project.rickandmorty.presentation.viewmodels.common.DetailViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocationDetailScreen(locationId: Int, navController: NavHostController) {
    val viewModel = hiltViewModel<DetailViewModel>()

    val locationState = produceState<Resource<Location>>(initialValue = Resource.Loading()) {
        value = viewModel.getLocationDetails(locationId)
    }.value

    val locationEntityState = produceState<LocationEntity?>(initialValue = null) {
        viewModel.getLocationDetailsDb(locationId).data?.collect {
            value = it
        }
    }.value

    val location: Location? = if (locationState is Resource.Error) {
        locationEntityState?.toLocation()
    } else {
        locationState.data
    }

    val characters: MutableList<Character?> = mutableListOf()

    location?.residents?.forEach { item ->
        val characterId = item.substringAfterLast('/').toInt()
        val characterState = produceState<Resource<Character>>(initialValue = Resource.Loading()) {
            value = viewModel.getCharacterDetails(characterId)
        }.value

        val characterEntityState = produceState<CharacterEntity?>(initialValue = null) {
            viewModel.getCharacterDetailsDb(characterId).data?.collect {
                value = it
            }
        }.value

        val character: Character? = if (characterState is Resource.Error) {
            characterEntityState?.toCharacter()
        } else {
            characterState.data
        }
        characters.add(character)
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                modifier = Modifier,
                title = {},
                navigationIcon = {
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
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Black,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                )
            )
        },
        content = { innerPadding ->
            EpisodeDetails(
                location = location,
                navController = navController,
                characters = characters,
                modifier = Modifier.padding(innerPadding)
            )
        })
}

@Composable
fun EpisodeDetails(
    location: Location?,
    navController: NavController,
    characters: MutableList<Character?>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = Black),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        if (location != null) {
            Box(
                modifier = Modifier
                    .wrapContentHeight()
                    .background(color = Black)
            )

            val fieldText = mapOf(
                Pair("name", location.name),
                Pair("type", location.type),
                Pair("dimension", location.dimension)
            )

            InfoBox(
                fieldText = fieldText, modifier = Modifier.wrapContentHeight()
            )

            NestedCharactersGrid(
                characters = characters,
                navController = navController
            )
        }
    }

}


@Composable
fun InfoBox(
    fieldText: Map<String, String>, modifier: Modifier = Modifier
) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .drawBehind {
            drawRoundRect(
                Color(0xFFBBAAEE), cornerRadius = CornerRadius(10.dp.toPx())
            )
        }
        .padding(4.dp)
        .background(color = MaterialTheme.colorScheme.primary.copy(alpha = 0.6f))) {
        Column {
            for ((key, value) in fieldText) {
                Box(
                    modifier = modifier.background(color = Transparent)

                ) {
                    Text(
                        text = "$key: $value",
                        color = White,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }

        }
    }
}

