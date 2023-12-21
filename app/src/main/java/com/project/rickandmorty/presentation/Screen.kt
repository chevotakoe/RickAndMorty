package com.project.rickandmorty.presentation

sealed class Screen(val route: String) {
    object CharacterDetailScreen : Screen("character_details_screen")
    object EpisodeDetailScreen : Screen("episode_details_screen")
    object LocationDetailScreen : Screen("location_details_screen")

    object CharacterSearchResultsScreen : Screen("character_search_results_screen")
    object EpisodeSearchResultsScreen : Screen("episode_search_results_screen")
    object LocationSearchResultsScreen : Screen("location_search_results_screen")

    object StartScreen : Screen("start_grids_screen")
}
