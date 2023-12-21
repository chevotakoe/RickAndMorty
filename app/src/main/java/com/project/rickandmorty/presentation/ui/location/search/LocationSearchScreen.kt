package com.project.rickandmorty.presentation.ui.location.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
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
import com.project.rickandmorty.data.db.entities.LocationEntity
import com.project.rickandmorty.domain.models.Location
import com.project.rickandmorty.domain.models.mappers.toLocation
import com.project.rickandmorty.presentation.Resource
import com.project.rickandmorty.presentation.ui.common.LocationInfoBox
import com.project.rickandmorty.presentation.viewmodels.location.LocationSearchViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocationSearchScreen(
    name: String?, type: String?, dimension: String?, navController: NavController
) {
    val viewModel = hiltViewModel<LocationSearchViewModel>()

    val locationsState = produceState<Resource<List<Location>>>(initialValue = Resource.Loading()) {
        value = viewModel.getFilteredLocations(name, type, dimension)
    }.value

    val locations: List<Location>? = if (locationsState is Resource.Error) {
        produceState<List<LocationEntity>?>(initialValue = null) {
            viewModel.getLocationSearchDb(
                name = name ?: "",
                type = type ?: "",
                dimension = dimension ?: ""
            ).data?.collect {
                value = it
            }
        }.value?.map { it.toLocation() }
    } else {
        locationsState.data
    }

    Scaffold(topBar = {
        CenterAlignedTopAppBar(
            modifier = Modifier.padding(),
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
                if (locations != null) {
                    items(locations.size) { item ->
                        locations[item].let { location ->
                            val fieldText = mapOf(
                                Pair("name", location.name),
                                Pair("type", location.type),
                                Pair("dimension", location.dimension)
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

