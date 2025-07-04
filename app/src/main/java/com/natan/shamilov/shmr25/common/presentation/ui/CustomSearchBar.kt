package com.natan.shamilov.shmr25.common.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.natan.shamilov.shmr25.R

@Composable
fun CustomSearchBar(
    query: String,
    onValueChange: (String) -> Unit
) {
    val focusManager = LocalFocusManager.current
    val padding = if (query.isEmpty()) {
        PaddingValues(15.dp)
    } else {
        PaddingValues(top = 15.dp, start = 15.dp, bottom = 15.dp)
    }

    BasicTextField(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(MaterialTheme.colorScheme.surfaceContainerHigh)
            .padding(padding),
        value = query,
        singleLine = true,
        onValueChange = onValueChange,
        textStyle = LocalTextStyle.current.copy(color = MaterialTheme.colorScheme.onSurfaceVariant),
        cursorBrush = SolidColor(MaterialTheme.colorScheme.onSurface),
        decorationBox = { innerTextField ->
            Box(modifier = Modifier.fillMaxSize()) {
                if (query.isEmpty()) {
                    Text(stringResource(R.string.find_category))
                }

                innerTextField()

                TrailingIcon(query) { onValueChange("") }
            }
        },
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = { focusManager.clearFocus() }
        )
    )
}

@Composable
private fun BoxScope.TrailingIcon(
    query: String,
    onClear: () -> Unit
) {
    if (query.isEmpty()) {
        Icon(
            modifier = Modifier.align(Alignment.CenterEnd),
            imageVector = Icons.Default.Search,
            contentDescription = null
        )
    } else {
        IconButton(
            modifier = Modifier.align(Alignment.CenterEnd),
            onClick = onClear
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = null
            )
        }
    }
}
