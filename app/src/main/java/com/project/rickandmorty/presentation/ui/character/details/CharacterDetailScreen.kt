package com.project.rickandmorty.presentation.ui.character.details

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.project.rickandmorty.data.db.entities.CharacterEntity
import com.project.rickandmorty.data.db.entities.EpisodeEntity
import com.project.rickandmorty.domain.models.Character
import com.project.rickandmorty.domain.models.Episode
import com.project.rickandmorty.domain.models.mappers.toCharacter
import com.project.rickandmorty.domain.models.mappers.toEpisode
import com.project.rickandmorty.presentation.Resource
import com.project.rickandmorty.presentation.ui.common.EpisodeInfoBox
import com.project.rickandmorty.presentation.viewmodels.common.DetailViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterDetailScreen(characterId: Int, navController: NavController) {

    val viewModel = hiltViewModel<DetailViewModel>()

    val characterState = produceState<Resource<Character>>(initialValue = Resource.Loading()) {
        value = viewModel.getCharacterDetails(characterId = characterId)
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

    val episodes: MutableList<Episode?> = mutableListOf()

    character?.episode?.forEach { item ->
        val episodeId = item.substringAfterLast('/').toInt()
        val episodeState = produceState<Resource<Episode>>(initialValue = Resource.Loading()) {
            value = viewModel.getEpisodeDetails(episodeId)
        }.value

        val episodeEntityState = produceState<EpisodeEntity?>(initialValue = null) {
            viewModel.getEpisodeDetailsDb(episodeId).data?.collect {
                value = it
            }
        }.value

        val episode: Episode? = if (episodeState is Resource.Error) {
            episodeEntityState?.toEpisode()
        } else {
            episodeState.data
        }

        episodes.add(episode)
    }

    Scaffold(topBar = {
        CenterAlignedTopAppBar(
            modifier = Modifier,
            title = {},
            navigationIcon = {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Go Back",
                    modifier = Modifier
                        .size(width = 24.dp, height = 24.dp)
                        .clickable {
                            navController.popBackStack()
                        },
                    tint = Color(color = 0xFFBBAAEE)
                )
            },
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = Black,
                titleContentColor = MaterialTheme.colorScheme.primary,
            )
        )
    }) { innerPadding ->
        CharacterDetails(
            character = character,
            navController = navController,
            episodes = episodes,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
fun CharacterDetails(
    character: Character?,
    navController: NavController,
    episodes: MutableList<Episode?>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(color = Black),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        if (character != null) {
            item {
                Box(
                    modifier = Modifier
                        .wrapContentHeight()
                        .background(color = Black)
                ) {
                    AsyncImage(
                        modifier = Modifier.fillMaxWidth(),
                        model = character.image,
                        contentDescription = character.name
                    )
                }
            }
            val fieldText = mapOf(
                Pair("name", character.name),
                Pair("status", character.status),
                Pair("species", character.species),
                Pair("gender", character.gender),
                Pair("type", character.type)
            )

            item { CharacterBox(fieldText = fieldText) }

            item {
                val locations = mutableMapOf(
                    Pair("location", character.location),
                    Pair("origin", character.origin)
                )
                locations.forEach { item ->
                    Spacer(modifier = Modifier.height(4.dp))
                    LocationBox(field = item.key,
                        text = item.value.name,
                        modifier = Modifier.clickable {
                            navController.navigate(
                                "location_details_screen/${
                                    item.value.url.substringAfterLast('/').toInt()
                                }"
                            )
                        })
                }
            }

            item {
                EpisodeList(
                    episodes = episodes,
                    navController = navController
                )
            }
        }
    }
}

@Composable
fun CharacterBox(fieldText: Map<String, String>, modifier: Modifier = Modifier) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .drawBehind {
            drawRoundRect(
                Color(0xFFBBAAEE), cornerRadius = CornerRadius(10.dp.toPx())
            )
        }
        .padding(4.dp)
        .background(color = MaterialTheme.colorScheme.primary.copy(alpha = 0.6f))
        .wrapContentHeight()) {
        Column {
            for ((key, value) in fieldText) {
                Box(
                    modifier = modifier
                        .padding(start = 4.dp)
                        .background(color = Transparent)

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

@Composable
fun LocationBox(field: String, text: String, modifier: Modifier = Modifier) {
    Box(modifier = modifier
        .fillMaxWidth()
        .height(50.dp)
        .drawBehind {
            drawRoundRect(
                Color(0xFFBBAAEE), cornerRadius = CornerRadius(10.dp.toPx())
            )
        }
        .padding(4.dp)
        .background(color = MaterialTheme.colorScheme.primary.copy(alpha = 0.6f))

    ) {
        Text(
            text = "$field: $text",
            color = White,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(Alignment.Center)
                .padding(start = 4.dp)
        )
    }
}

@Composable
fun EpisodeList(episodes: MutableList<Episode?>, navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(10.dp))
        Box {
            Text(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .wrapContentHeight(),
                text = "EPISODES",
                color = White,
                fontSize = 20.sp
            )
        }
        episodes.forEach { episode ->
            episode?.let {
                val fieldText = mapOf(
                    Pair("name", episode.name),
                    Pair("airDate", episode.airDate),
                    Pair("episode", episode.episode)
                )
                EpisodeInfoBox(fieldText = fieldText, modifier = Modifier
                    .wrapContentHeight()
                    .clickable {
                        navController.navigate(
                            "episode_details_screen/${episode.id}"
                        )
                    })
            }
        }
    }

}
