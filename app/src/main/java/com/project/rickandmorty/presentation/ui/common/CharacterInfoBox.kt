package com.project.rickandmorty.presentation.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun CharacterInfoBox(
    fieldText: Map<String, String?>, modifier: Modifier = Modifier, image: String = "unknown"
) {
    Box(modifier = modifier
        .height(300.dp)
        .fillMaxWidth()
        .drawBehind {
            drawRoundRect(
                Color(0xFFBBAAEE), cornerRadius = CornerRadius(10.dp.toPx())
            )
        }
        .padding(4.dp)
        .background(color = MaterialTheme.colorScheme.primary.copy(alpha = 0.6f))) {
        Column(modifier = Modifier.padding(4.dp)) {
            if (image != "unknown") {
                Box(
                    modifier = Modifier
                        .wrapContentHeight()
                        .background(color = Color.Black)
                ) {
                    AsyncImage(
                        modifier = Modifier.fillMaxWidth(),
                        model = image,
                        contentDescription = fieldText["name"]
                    )
                }
            }
            for ((key, value) in fieldText) {
                Box(
                    modifier = modifier.background(color = Color.Transparent)

                ) {
                    Text(
                        text = "$key: $value",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }

        }
    }
}