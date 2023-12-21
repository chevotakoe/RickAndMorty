package com.project.rickandmorty.presentation.ui.episode.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.project.rickandmorty.data.db.entities.EpisodeEntity
import com.project.rickandmorty.domain.models.Episode
import com.project.rickandmorty.domain.models.mappers.toEpisode
import com.project.rickandmorty.presentation.Resource
import com.project.rickandmorty.presentation.ui.common.EpisodeInfoBox
import com.project.rickandmorty.presentation.viewmodels.episode.EpisodeSearchViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EpisodeSearchScreen(
    name: String?, episode: String?, navController: NavController
) {

    val viewModel = hiltViewModel<EpisodeSearchViewModel>()

    val episodesState = produceState<Resource<List<Episode>>>(initialValue = Resource.Loading()) {
        value = viewModel.getFilteredEpisodes(name, episode)
    }.value

    val episodesEntityState = produceState<List<EpisodeEntity>?>(initialValue = null) {
        viewModel.getEpisodeSearchDb(name, episode).data?.collect {
            value = it
        }
    }.value

    val episodes: List<Episode>? = if (episodesState is Resource.Error) {
        episodesEntityState?.map { it.toEpisode() }
    } else {
        episodesState.data
    }


    Scaffold(topBar = {
        CenterAlignedTopAppBar(
            modifier = Modifier.padding(),
            title = {},
            navigationIcon = {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = null,
                    modifier = Modifier
                        .size(24.dp, 24.dp)
                        .clickable {
                            navController.popBackStack()
                        },
                    tint = Color(0xFFBBAAEE)
                )
            },
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = Color.Transparent,
                titleContentColor = MaterialTheme.colorScheme.primary,
            )
        )
    }, content = {
        LazyVerticalGrid(columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(space = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(space = 16.dp),
            contentPadding = PaddingValues(all = 10.dp),
            modifier = Modifier.padding(it),
            content = {
                if (episodes != null) {
                    items(episodes.size) { item ->
                        episodes[item].let { episode ->
                            val fieldText = mapOf(
                                Pair("name", episode.name), Pair("episode", episode.episode)
                            )
                            EpisodeInfoBox(fieldText = fieldText,
                                modifier = Modifier
                                    .height(210.dp)
                                    .clickable {
                                        navController.navigate(
                                            "episode_details_screen/${episode.id}"
                                        )
                                    })
                        }

                    }
                } else {

                    item {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "No items found"
                            )
                        }
                    }

                }

            })
    })
}

