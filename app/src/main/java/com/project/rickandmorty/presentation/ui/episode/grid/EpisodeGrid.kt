package com.project.rickandmorty.presentation.ui.episode.grid

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.project.rickandmorty.presentation.ui.common.EpisodeInfoBox
import com.project.rickandmorty.presentation.viewmodels.episode.EpisodeListViewModel


@Composable
fun EpisodeGrid(navController: NavController) {

    val viewModel = hiltViewModel<EpisodeListViewModel>()
    val episodes = viewModel.episodePagingFlow.collectAsLazyPagingItems()
    val context = LocalContext.current

    LaunchedEffect(key1 = episodes.loadState) {
        if (episodes.loadState.refresh is LoadState.Error) {
            Toast.makeText(
                context,
                "Error occurred " + (episodes.loadState.refresh as LoadState.Error).error.message,
                Toast.LENGTH_LONG
            ).show()
        }
    }
    Column {
        SearchBar(
            hint = "Tap to search",
            modifier = Modifier
                .width(150.dp)
                .padding(4.dp),
            navController = navController
        )
        if (episodes.loadState.refresh is LoadState.Loading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black)
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        } else if (episodes.itemCount == 0) {
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
                content = {
                    items(episodes.itemCount) { item ->
                        episodes[item]?.let { episode ->
                            val fieldText = mapOf(
                                Pair("name", episode.name),
                                Pair("airDate", episode.airDate),
                                Pair("episode", episode.episode),
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
                    item {
                        if (episodes.loadState.append is LoadState.Loading) {
                            CircularProgressIndicator()
                        }
                    }

                })
        }
    }
}


@Composable
fun SearchBar(
    modifier: Modifier = Modifier, hint: String = "", navController: NavController
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf("name") }

    var name by remember {
        mutableStateOf("")
    }
    var episode by remember {
        mutableStateOf("")
    }


    var isHintVisible by remember {
        mutableStateOf(hint != "")
    }

    Row(horizontalArrangement = Arrangement.SpaceEvenly) {
        Column(modifier = Modifier.padding(start = 5.dp)) {
            Text(selectedOption)
            Box {
                IconButton(onClick = { expanded = true }) {
                    Icon(Icons.Default.ArrowDropDown, contentDescription = "ShowMenu")
                }
                DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                    DropdownMenuItem(onClick = {
                        selectedOption = "name"
                        expanded = false
                    }, text = { Text("name") })
                    Divider()
                    DropdownMenuItem(onClick = {
                        selectedOption = "episode"
                        expanded = false
                    }, text = { Text("episode") })

                }
            }
        }

        Box(modifier = modifier) {
            BasicTextField(value = when (selectedOption) {
                "name" -> name
                else -> {
                    episode
                }
            },
                onValueChange = { newText: String ->
                    when (selectedOption) {
                        "name" -> name = newText
                        else -> {
                            episode = newText
                        }
                    }
                },
                maxLines = 1,
                singleLine = true,
                textStyle = TextStyle(color = Color.Black),
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(7.dp, CircleShape)
                    .background(Color.White)
                    .padding(horizontal = 25.dp, vertical = 12.dp)
                    .onFocusChanged {
                        isHintVisible = when (selectedOption) {
                            "name" -> !it.hasFocus && name.isNotEmpty()
                            else -> {
                                !it.hasFocus && episode.isNotEmpty()
                            }
                        }
                    }

            )
            if (isHintVisible) {
                Text(
                    text = hint,
                    color = Color.LightGray,
                    modifier = Modifier.padding(horizontal = 25.dp, vertical = 12.dp)
                )
            }
        }
        Button(modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth()
            .height(50.dp), onClick = {
            navController.navigate(
                "episode_search_results_screen?name=${name}&episode=${episode}"
            )
        }) {
            Icon(
                imageVector = Icons.Default.Search, contentDescription = "Search"
            )
        }
    }

}