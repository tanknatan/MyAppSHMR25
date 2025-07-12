package com.natan.shamilov.shmr25.common.impl.presentation.ui

import android.app.TimePickerDialog
import android.widget.TimePicker
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.platform.LocalContext
import java.time.LocalTime

@Composable
fun CustomTimePickerDialog(
    initialTime: LocalTime,
    onDismissRequest: () -> Unit,
    onClear: () -> Unit,
    onTimeSelected: (LocalTime) -> Unit,
) {
    val context = LocalContext.current

    DisposableEffect(Unit) {
        val listener = TimePickerDialog.OnTimeSetListener { _: TimePicker, hour: Int, minute: Int ->
            onTimeSelected(LocalTime.of(hour, minute))
        }

        val dialog = TimePickerDialog(
            context,
            listener,
            initialTime.hour,
            initialTime.minute,
            true // 24-hour format
        )

        dialog.setButton(TimePickerDialog.BUTTON_NEGATIVE, "Отмена") { _, _ -> onDismissRequest() }
        dialog.setButton(TimePickerDialog.BUTTON_NEUTRAL, "Очистить") { _, _ -> onClear() }

        dialog.show()

        onDispose {
            dialog.dismiss()
        }
    }
}
