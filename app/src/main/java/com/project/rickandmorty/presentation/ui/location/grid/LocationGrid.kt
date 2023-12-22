package com.project.rickandmorty.presentation.ui.location.grid

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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
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
import com.project.rickandmorty.presentation.ui.common.LocationInfoBox
import com.project.rickandmorty.presentation.viewmodels.location.LocationListViewModel


@Composable
fun LocationGrid(navController: NavController) {

    val viewModel = hiltViewModel<LocationListViewModel>()
    val context = LocalContext.current
    val locations = viewModel.locationPagingFlow.collectAsLazyPagingItems()

    LaunchedEffect(key1 = locations.loadState) {
        if (locations.loadState.refresh is LoadState.Error) {
            Toast.makeText(
                context,
                "Error occurred " + (locations.loadState.refresh as LoadState.Error).error.message,
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
        if (locations.loadState.refresh is LoadState.Loading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black)
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        } else if (locations.itemCount == 0) {
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
                    items(locations.itemCount) { item ->
                        locations[item]?.let { location ->
                            val fieldText = mapOf(
                                Pair("name", location.name),
                                Pair("type", location.type),
                                Pair("dimension", location.dimension),
                            )
                            LocationInfoBox(fieldText = fieldText,
                                modifier = Modifier
                                    .wrapContentWidth()
                                    .wrapContentHeight()
                                    .clickable {
                                        navController.navigate(
                                            "location_details_screen/${location.id}"
                                        )
                                    })
                        }

                    }
                    item {
                        if (locations.loadState.append is LoadState.Loading) {
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
    var type by remember {
        mutableStateOf("")
    }
    var dimension by remember {
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
                        selectedOption = "dimension"
                        expanded = false
                    }, text = { Text("dimension") })
                    Divider()
                    DropdownMenuItem(onClick = {
                        selectedOption = "type"
                        expanded = false
                    }, text = { Text("type") })

                }
            }
        }

        Box(modifier = modifier) {
            BasicTextField(value = when (selectedOption) {
                "name" -> name
                "type" -> type
                else -> {
                    dimension
                }
            },
                onValueChange = { newText: String ->
                    when (selectedOption) {
                        "name" -> name = newText
                        "type" -> type = newText
                        else -> {
                            dimension = newText
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
                            "type" -> !it.hasFocus && type.isNotEmpty()
                            else -> {
                                !it.hasFocus && dimension.isNotEmpty()
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
                "location_search_results_screen?name=${name}&type=${type}&dimension=${dimension}"
            )
        }) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search"
            )
        }
    }

}