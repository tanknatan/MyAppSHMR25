package com.natan.shamilov.shmr25.common.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.natan.shamilov.shmr25.R
import com.natan.shamilov.shmr25.common.ui.theme.BottomBarBackground
import com.natan.shamilov.shmr25.common.ui.theme.rodotoFont

@Composable
fun SearchField(
    query: String,
    onQueryChange: (String) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(BottomBarBackground)
            .drawBehind {
                val strokeWidth = 0.7.dp.toPx()
                drawLine(
                    color = Color(0xFFCAC4D0),
                    start = Offset(0f, size.height - strokeWidth / 2),
                    end = Offset(size.width, size.height - strokeWidth / 2),
                    strokeWidth = strokeWidth
                )
            }
            .padding(horizontal = 8.dp)
    ) {
        TextField(
            value = query,
            onValueChange = onQueryChange,
            modifier = Modifier.fillMaxWidth(),
            placeholder = {
                Text(
                    text = stringResource(R.string.find_category),
                    fontSize = 16.sp,
                    color = Color(0xFF49454F),
                    style = MaterialTheme.typography.bodyLarge,
                    fontFamily = rodotoFont
                )
            },
            colors = TextFieldDefaults.colors(
                focusedTextColor = Color(0xFF49454F),
                unfocusedTextColor = Color(0xFFECE6F0),
                focusedIndicatorColor = Color(0xFFCAC4D0),
                unfocusedIndicatorColor = Color(0xFFCAC4D0),
                focusedPlaceholderColor = Color(0xFFECE6F0),
                unfocusedPlaceholderColor = Color(0xFFECE6F0),
                focusedContainerColor = Color(0xFFECE6F0),
                unfocusedContainerColor = Color(0xFFECE6F0)
            ),
            singleLine = true
        )

        Image(
            painter = painterResource(R.drawable.ic_search),
            contentDescription = null,
            modifier = Modifier.align(Alignment.CenterEnd)
                .padding(end = 10.dp)
                .clickable {}
        )
    }
}
