package com.natan.shamilov.shmr25.common.ui

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
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.natan.shamilov.shmr25.R
import com.natan.shamilov.shmr25.common.ui.theme.dep
import com.natan.shamilov.shmr25.common.ui.theme.rodotoFont

/**
 * Универсальная карточка приложения.
 * Отображает информацию в виде карточки с возможностью настройки заголовка,
 * подзаголовка, суммы, дополнительной суммы и эмодзи-аватара.
 *
 * @param title Основной заголовок карточки
 * @param subtitle Подзаголовок (опционально)
 * @param amount Числовое значение суммы (опционально)
 * @param subAmount Дополнительная текстовая информация о сумме (опционально)
 * @param avatarEmoji Эмодзи для аватара (опционально)
 * @param canNavigate Флаг, указывающий возможность навигации
 * @param onNavigateClick Callback для обработки нажатия при навигации
 * @param isSetting Флаг, указывающий является ли карточка элементом настроек
 */
@JvmOverloads
@Composable
fun AppCard(
    title: String,
    subtitle: String? = null,
    amount: Double? = null,
    subAmount: String? = null,
    avatarEmoji: String? = null,
    canNavigate: Boolean = false,
    onNavigateClick: (() -> Unit)? = null,
    isSetting: Boolean = false
) {
    val borderColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.1f)
    Card(
        modifier = if (canNavigate && onNavigateClick != null) {
            Modifier
                .fillMaxWidth()
                .height(70.dep)
                .clickable { }
        } else {
            Modifier
                .fillMaxWidth()
                .height(70.dep)
        },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 0.dp,
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
                .height(70.dep)
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
                        .background(MaterialTheme.colorScheme.secondary),
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
                    fontFamily = rodotoFont

                )
                if (!subtitle.isNullOrEmpty()) {
                    Text(
                        text = subtitle,
                        style = MaterialTheme.typography.bodyMedium,
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontFamily = rodotoFont
                    )
                }
            }

            if (amount != null) {
                Column(
                    horizontalAlignment = Alignment.End
                ) {
                    Text(
                        text = amount.toCurrencyString(),
                        style = MaterialTheme.typography.bodyLarge,
                        fontSize = 16.sp,
                        fontFamily = rodotoFont
                    )
                    if (!subAmount.isNullOrEmpty()) {
                        Text(
                            text = subAmount,
                            style = MaterialTheme.typography.bodyMedium,
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            textAlign = TextAlign.End,
                            fontFamily = rodotoFont
                        )
                    }
                }
                Spacer(modifier = Modifier.width(16.dp))
            }

            if (canNavigate && onNavigateClick != null) {
                if (isSetting) {
                    Image(
                        painter = painterResource(R.drawable.ic_category_more),
                        contentDescription = null,
                        modifier = Modifier
                            .size(24.dp)
                    )
                } else {
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
}
