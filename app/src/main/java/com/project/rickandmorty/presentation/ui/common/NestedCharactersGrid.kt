package com.project.rickandmorty.presentation.ui.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.project.rickandmorty.domain.models.Character


@Composable
fun NestedCharactersGrid(
    characters: MutableList<Character?>, navController: NavController
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Box {
            Text(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .wrapContentHeight(),
                text = "CHARACTERS",
                color = Color.White,
                fontSize = 20.sp
            )
        }

        LazyVerticalGrid(columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(space = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(space = 16.dp),
            contentPadding = PaddingValues(all = 10.dp),
            content = {
                items(characters.count()) { item ->
                    characters[item]?.let { character ->
                        val fieldText = mapOf(
                            Pair("name", character.name),
                            Pair("status", character.status),
                            Pair("species", character.species),
                            Pair("gender", character.gender)
                        )
                        CharacterInfoBox(fieldText = fieldText,
                            image = character.image,
                            modifier = Modifier
                                .clickable {
                                    navController.navigate(
                                        "character_details_screen/${character.id}"
                                    )
                                })
                    }

                }

            })
    }

}