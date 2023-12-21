package com.project.rickandmorty.presentation

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.project.rickandmorty.presentation.ui.StartScreen
import com.project.rickandmorty.presentation.ui.character.details.CharacterDetailScreen
import com.project.rickandmorty.presentation.ui.character.search.CharacterSearchScreen
import com.project.rickandmorty.presentation.ui.episode.details.EpisodeDetailScreen
import com.project.rickandmorty.presentation.ui.episode.search.EpisodeSearchScreen
import com.project.rickandmorty.presentation.ui.location.details.LocationDetailScreen
import com.project.rickandmorty.presentation.ui.location.search.LocationSearchScreen
import com.project.rickandmorty.ui.theme.RickAndMortyTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            RickAndMortyTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController, startDestination = Screen.StartScreen.route
                    ) {
                        composable(
                            route = Screen.StartScreen.route
                        ) {
                            StartScreen(navController)
                        }
                        composable(route = Screen.CharacterDetailScreen.route + "/{characterId}",
                            arguments = listOf(navArgument("characterId") {
                                type = NavType.IntType
                            })
                        ) {
                            val characterId = remember {
                                it.arguments?.getInt("characterId") ?: 1
                            }
                            CharacterDetailScreen(characterId, navController)
                        }
                        composable(route = Screen.LocationDetailScreen.route + "/{locationId}",
                            arguments = listOf(navArgument("locationId") {
                                type = NavType.IntType
                            })
                        ) {
                            val locationId = remember {
                                it.arguments?.getInt("locationId") ?: 1
                            }
                            LocationDetailScreen(locationId, navController)
                        }
                        composable(route = Screen.EpisodeDetailScreen.route + "/{episodeId}",
                            arguments = listOf(navArgument("episodeId") {
                                type = NavType.IntType
                            })
                        ) {
                            val episodeId = remember {
                                it.arguments?.getInt("episodeId") ?: 1
                            }
                            EpisodeDetailScreen(episodeId, navController)
                        }
                        composable(route = Screen.CharacterSearchResultsScreen.route + "?name={name}&status={status}&gender={gender}&species={species}&type={type}",
                            arguments = listOf(navArgument("name") {
                                nullable = true
                                type = NavType.StringType
                            }, navArgument("status") {
                                nullable = true
                                type = NavType.StringType
                            }, navArgument("gender") {
                                nullable = true
                                type = NavType.StringType
                            }, navArgument("species") {
                                nullable = true
                                type = NavType.StringType
                            }, navArgument("type") {
                                nullable = true
                                type = NavType.StringType
                            })
                        ) {
                            val name = remember {
                                it.arguments?.getString("name")
                            }
                            val status = remember {
                                it.arguments?.getString("status")
                            }
                            val gender = remember {
                                it.arguments?.getString("gender")
                            }
                            val type = remember {
                                it.arguments?.getString("type")
                            }
                            val species = remember {
                                it.arguments?.getString("species")
                            }
                            CharacterSearchScreen(
                                name = name,
                                status = status,
                                gender = gender,
                                species = species,
                                type = type,
                                navController = navController
                            )

                        }
                        composable(route = Screen.EpisodeSearchResultsScreen.route + "?name={name}&episode={episode}",
                            arguments = listOf(navArgument("name") {
                                nullable = true
                                type = NavType.StringType
                            }, navArgument("episode") {
                                nullable = true
                                type = NavType.StringType
                            })
                        ) {
                            val name = remember {
                                it.arguments?.getString("name")
                            }
                            val episode = remember {
                                it.arguments?.getString("episode")
                            }
                            EpisodeSearchScreen(
                                name = name, episode = episode, navController = navController
                            )

                        }
                        composable(route = Screen.LocationSearchResultsScreen.route + "?name={name}&type={type}&dimension={dimension}",
                            arguments = listOf(navArgument("name") {
                                nullable = true
                                type = NavType.StringType
                            }, navArgument("type") {
                                nullable = true
                                type = NavType.StringType
                            }, navArgument("dimension") {
                                nullable = true
                                type = NavType.StringType
                            })
                        ) {
                            val name = remember {
                                it.arguments?.getString("name")
                            }
                            val type = remember {
                                it.arguments?.getString("type")
                            }
                            val dimension = remember {
                                it.arguments?.getString("dimension")
                            }
                            LocationSearchScreen(
                                name = name,
                                type = type,
                                dimension = dimension,
                                navController = navController
                            )

                        }
                    }
                }
            }
        }
    }
}


