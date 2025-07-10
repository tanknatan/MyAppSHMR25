package com.natan.shamilov.shmr25.common.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.natan.shamilov.shmr25.common.R
import com.natan.shamilov.shmr25.common.presentation.ui.theme.dep
import com.natan.shamilov.shmr25.common.presentation.ui.theme.rodotoFont
import com.natan.shamilov.shmr25.common.presentation.utils.toCurrencyString

@JvmOverloads
@Composable
fun TopGreenCard(
    title: String,
    amount: Double? = null,
    cucurrency: String? = null,
    currency: String? = null,
    avatarEmoji: String? = null,
    canNavigate: Boolean = false,
    onNavigateClick: (() -> Unit)? = null
) {
    val borderColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.1f)
    Card(
        modifier = if (onNavigateClick != null) {
            Modifier
                .fillMaxWidth()
                .height(56.dep)
                .clickable { onNavigateClick() }
        } else {
            Modifier
                .fillMaxWidth()
                .height(56.dep)
        },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondary
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 0.dp,
            pressedElevation = 0.dp,
            hoveredElevation = 0.dp,
            focusedElevation = 0.dp,
            draggedElevation = 0.dp,
            disabledElevation = 0.dp
        ),
        shape = RectangleShape
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dep)
                .drawBehind {
                    val strokeWidth = 1.dp.toPx()
                    val y = size.height - strokeWidth / 2

                    drawLine(
                        color = borderColor,
                        start = Offset(0f, y),
                        end = Offset(size.width, y),
                        strokeWidth = strokeWidth
                    )
                }
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (avatarEmoji != null) {
                Box(
                    modifier = Modifier
                        .size(30.dep)
                        .clip(CircleShape)
                        .background(Color.White),
                    contentAlignment = Alignment.Center
                ) {
                    Text(avatarEmoji, fontSize = 19.sp, fontFamily = rodotoFont)
                }
                Spacer(modifier = Modifier.width(12.dp))
            }
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyLarge,
                    fontSize = 16.sp,
                    color = Color(0xFF1D1B20),
                    fontFamily = rodotoFont
                )
            }
            if (amount != null) {
                Text(
                    text = amount.toCurrencyString(""),
                    style = MaterialTheme.typography.bodyLarge,
                    fontSize = 16.sp,
                    color = Color(0xFF1D1B20),
                    fontFamily = rodotoFont
                )
                Spacer(modifier = Modifier.width(8.dp))
            }
            if (cucurrency != null) {
                Text(
                    text = cucurrency,
                    style = MaterialTheme.typography.bodyLarge,
                    fontSize = 16.sp,
                    color = Color(0xFF1D1B20),
                    fontFamily = rodotoFont
                )
                Spacer(modifier = Modifier.width(8.dp))
            }

            if (canNavigate) {
                Image(
                    painter = painterResource(R.drawable.ic_more),
                    contentDescription = "Подробнее",
                    modifier = Modifier
                        .size(24.dp)
                )
            }
        }
    }
}
