package com.project.rickandmorty.presentation.ui

import android.annotation.SuppressLint
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.outlined.Face
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.PlayArrow
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import com.project.rickandmorty.presentation.ui.character.grid.CharacterGrid
import com.project.rickandmorty.presentation.ui.episode.grid.EpisodeGrid
import com.project.rickandmorty.presentation.ui.location.grid.LocationGrid

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StartScreen(navController: NavController) {
    val items = listOf(
        BottomNavigationItem(
            title = "Characters",
            selectedIcon = Icons.Filled.Face,
            unselectedIcon = Icons.Outlined.Face
        ),
        BottomNavigationItem(
            title = "Locations",
            selectedIcon = Icons.Filled.LocationOn,
            unselectedIcon = Icons.Outlined.LocationOn
        ),
        BottomNavigationItem(
            title = "Episodes",
            selectedIcon = Icons.Filled.PlayArrow,
            unselectedIcon = Icons.Outlined.PlayArrow
        ),
    )

    var selectedItemIndex by rememberSaveable {
        mutableStateOf(0)
    }
    Scaffold(bottomBar = {
        NavigationBar {
            items.forEachIndexed { index, item ->
                NavigationBarItem(selected = selectedItemIndex == index, onClick = {
                    selectedItemIndex = index
                }, label = {
                    Text(text = item.title)
                }, icon = {
                    Icon(
                        imageVector = if (index == selectedItemIndex) {
                            item.selectedIcon
                        } else item.unselectedIcon, contentDescription = item.title
                    )
                })
            }
        }

    }, content = {
        when (items[selectedItemIndex].title) {
            "Characters" -> CharacterGrid(navController)
            "Episodes" -> EpisodeGrid(navController)
            else -> LocationGrid(navController)
        }
    })
}

data class BottomNavigationItem(
    val title: String, val selectedIcon: ImageVector, val unselectedIcon: ImageVector
)