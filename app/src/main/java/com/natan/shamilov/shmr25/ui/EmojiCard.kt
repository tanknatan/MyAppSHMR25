package com.example.shmrfinance.ui.widget.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun EmojiCard(
    backgroundColor: Color = MaterialTheme.colorScheme.secondaryContainer,
    emoji: String
) {
    val size = if (emoji.isWord())
        11.sp
    else
        TextUnit.Unspecified

    Text(
        modifier = Modifier
            .size(24.dp)
            .background(
                color = backgroundColor,
                shape = CircleShape
            ),
        text = emoji,
        fontSize = size,
        fontWeight = FontWeight.Medium,
        textAlign = TextAlign.Center
    )
}

private fun String.isWord(): Boolean {
    return matches(Regex("^[а-яА-ЯёЁ]+${'$'}"))
}