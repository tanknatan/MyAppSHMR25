package com.natan.shamilov.shmr25.ui

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import androidx.compose.material3.DatePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDatePickerState


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePicker() {
    val context = LocalContext.current
    val datePickerState = rememberDatePickerState()
    val openDialog = remember { mutableStateOf(false) }

    val selectedDateMillis = datePickerState.selectedDateMillis
    val formattedDate = selectedDateMillis?.let {
        val sdf = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        sdf.format(Date(it))
    } ?: "Выбери дату"

    Column(modifier = Modifier.padding(16.dp)) {
        Button(onClick = { openDialog.value = true }) {
            Text(text = formattedDate)
        }
//
//        if (openDialog.value) {
//            DatePickerDialog(
//                onDismissRequest = { openDialog.value = false },
//                confirmButton = {
//                    TextButton(onClick = { openDialog.value = false }) {
//                        Text("OK")
//                    }
//                },
//                dismissButton = {
//                    TextButton(onClick = { openDialog.value = false }) {
//                        Text("Cancel")
//                    }
//                }
//            ) {
//                DatePicker(state = datePickerState)
//            }
//        }
    }
}
