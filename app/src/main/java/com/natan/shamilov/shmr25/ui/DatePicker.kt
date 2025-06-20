package com.natan.shamilov.shmr25.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import java.time.Month
import java.time.format.TextStyle
import java.util.Calendar
import java.util.Locale


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomDatePickerDialog(
    onDismissRequest: () -> Unit,
    onDateSelected: (Long?) -> Unit,
    onClear: () -> Unit,
    initialDate: Long? = null,
) {
    val calendar = remember { Calendar.getInstance() }
    initialDate?.let { calendar.timeInMillis = it }

    var selectedDate by remember { mutableStateOf(calendar.timeInMillis) }

    var displayedMonth by remember { mutableIntStateOf(calendar.get(Calendar.MONTH)) }
    var displayedYear by remember { mutableIntStateOf(calendar.get(Calendar.YEAR)) }

    val daysInMonth = remember(displayedMonth, displayedYear) {
        Calendar.getInstance().apply {
            set(Calendar.YEAR, displayedYear)
            set(Calendar.MONTH, displayedMonth)
            set(Calendar.DAY_OF_MONTH, 1)
        }.getActualMaximum(Calendar.DAY_OF_MONTH)
    }

    // Dropdown state
    var showMonthDropdown by remember { mutableStateOf(false) }
    var showYearDropdown by remember { mutableStateOf(false) }

    val monthNames = Month.values().map {
        it.getDisplayName(TextStyle.SHORT, Locale.getDefault())
    }
    val yearRange = (2000..2100).toList()

    AlertDialog(
        onDismissRequest = onDismissRequest,
//        confirmButton = {},
//        dismissButton = {},
        content = {
            Column(
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color(0xFFE6FFF2))
            ) {
                // Шапка: выбор месяца и года
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // Месяц
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        IconButton(onClick = {
                            if (displayedMonth == 0) {
                                displayedMonth = 11
                                displayedYear -= 1
                            } else {
                                displayedMonth -= 1
                            }
                        }) {
                            Icon(Icons.Default.KeyboardArrowLeft, contentDescription = "Previous month")
                        }

                        Box {
                            TextButton(onClick = { showMonthDropdown = true }) {
                                Text(monthNames[displayedMonth])
                                Icon(Icons.Default.ArrowDropDown, contentDescription = null)
                            }
                            DropdownMenu(
                                expanded = showMonthDropdown,
                                onDismissRequest = { showMonthDropdown = false }
                            ) {
                                monthNames.forEachIndexed { index, name ->
                                    DropdownMenuItem(
                                        text = { Text(name) },
                                        onClick = {
                                            displayedMonth = index
                                            showMonthDropdown = false
                                        }
                                    )
                                }
                            }
                        }

                        IconButton(onClick = {
                            if (displayedMonth == 11) {
                                displayedMonth = 0
                                displayedYear += 1
                            } else {
                                displayedMonth += 1
                            }
                        }) {
                            Icon(Icons.Default.KeyboardArrowRight, contentDescription = "Next month")
                        }
                    }

                    // Год
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        IconButton(onClick = { displayedYear-- }) {
                            Icon(Icons.Default.KeyboardArrowLeft, contentDescription = "Previous year")
                        }

                        Box {
                            TextButton(onClick = { showYearDropdown = true }) {
                                Text(displayedYear.toString())
                                Icon(Icons.Default.ArrowDropDown, contentDescription = null)
                            }

                            DropdownMenu(
                                expanded = showYearDropdown,
                                onDismissRequest = { showYearDropdown = false }
                            ) {
                                yearRange.forEach { year ->
                                    DropdownMenuItem(
                                        text = { Text(year.toString()) },
                                        onClick = {
                                            displayedYear = year
                                            showYearDropdown = false
                                        }
                                    )
                                }
                            }
                        }

                        IconButton(onClick = { displayedYear++ }) {
                            Icon(Icons.Default.KeyboardArrowRight, contentDescription = "Next year")
                        }
                    }
                }

                // Сетка дней месяца
                val days = (1..daysInMonth).toList()
                val dayHeaders = listOf("S", "M", "T", "W", "T", "F", "S")

                LazyVerticalGrid(
                    columns = GridCells.Fixed(7),
                    modifier = Modifier.height(280.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    // Заголовки дней недели (начиная с понедельника)
                    items(dayHeaders.size) { index ->
                        Box(
                            modifier = Modifier.size(40.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = dayHeaders[index],
                                style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold),
                                color = Color.Gray
                            )
                        }
                    }

                    // Сдвиг: вычисляем offset под начало месяца
                    val firstDayOffset = Calendar.getInstance().apply {
                        set(Calendar.YEAR, displayedYear)
                        set(Calendar.MONTH, displayedMonth)
                        set(Calendar.DAY_OF_MONTH, 1)
                    }.let {
                        val dayOfWeek = it.get(Calendar.DAY_OF_WEEK) // 1 = Sunday ... 7 = Saturday
                        // Сдвигаем так, чтобы 1 = Monday, 7 = Sunday
                        (dayOfWeek + 5) % 7
                    }

                    // Пустые ячейки до первого числа
                    items(firstDayOffset) {
                        Box(modifier = Modifier.size(40.dp))
                    }

                    // Дни месяца
                    items(days.size) { index ->
                        val day = index + 1
                        val isSelected = Calendar.getInstance().apply {
                            timeInMillis = selectedDate
                        }.let {
                            it.get(Calendar.DAY_OF_MONTH) == day &&
                                    it.get(Calendar.MONTH) == displayedMonth &&
                                    it.get(Calendar.YEAR) == displayedYear
                        }

                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(if (isSelected) Color(0xFF00C853) else Color.Transparent)
                                .clickable {
                                    val selectedCal = Calendar.getInstance().apply {
                                        set(Calendar.YEAR, displayedYear)
                                        set(Calendar.MONTH, displayedMonth)
                                        set(Calendar.DAY_OF_MONTH, day)
                                    }
                                    selectedDate = selectedCal.timeInMillis
                                }
                        ) {
                            Text(
                                text = day.toString(),
                                color = if (isSelected) Color.White else Color.Black,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }


//                LazyVerticalGrid(
//                    columns = GridCells.Fixed(7),
//                    modifier = Modifier.height(280.dp),
//                    horizontalArrangement = Arrangement.Center
//                ) {
//                    // ✅ Заголовки дней недели
//                    items(dayHeaders.size) { index ->
//                        Box(
//                            modifier = Modifier
//                                .size(40.dp),
//                            contentAlignment = Alignment.Center
//                        ) {
//                            Text(
//                                text = dayHeaders[index],
//                                style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold),
//                                color = Color.Gray
//                            )
//                        }
//                    }
//
//                    // 📌 Пустые ячейки до первого дня месяца
//                    val firstDayOffset = Calendar.getInstance().apply {
//                        set(Calendar.YEAR, displayedYear)
//                        set(Calendar.MONTH, displayedMonth)
//                        set(Calendar.DAY_OF_MONTH, 1)
//                    }.get(Calendar.DAY_OF_WEEK) - 1 // Sunday = 1, Saturday = 7
//
//                    items(firstDayOffset) {
//                        Box(modifier = Modifier.size(40.dp)) // пустая ячейка
//                    }
//
//                    // 📅 Дни месяца
//                    items(days.size) { index ->
//                        val day = index + 1
//                        val isSelected = Calendar.getInstance().apply {
//                            timeInMillis = selectedDate
//                        }.let {
//                            it.get(Calendar.DAY_OF_MONTH) == day &&
//                                    it.get(Calendar.MONTH) == displayedMonth &&
//                                    it.get(Calendar.YEAR) == displayedYear
//                        }
//
//                        Box(
//                            contentAlignment = Alignment.Center,
//                            modifier = Modifier
//                                .size(40.dp)
//                                .clip(CircleShape)
//                                .background(if (isSelected) Color(0xFF00C853) else Color.Transparent)
//                                .clickable {
//                                    val selectedCal = Calendar.getInstance().apply {
//                                        set(Calendar.YEAR, displayedYear)
//                                        set(Calendar.MONTH, displayedMonth)
//                                        set(Calendar.DAY_OF_MONTH, day)
//                                    }
//                                    selectedDate = selectedCal.timeInMillis
//                                }
//                        ) {
//                            Text(
//                                text = day.toString(),
//                                color = if (isSelected) Color.White else Color.Black,
//                                style = MaterialTheme.typography.bodyMedium
//                            )
//                        }
//                    }
//                }

                Spacer(modifier = Modifier.height(8.dp))

                // Кнопки внизу
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    TextButton(onClick = {
                        onClear()
                        onDismissRequest()
                    }) {
                        Text("Clear")
                    }

                    Row {
                        TextButton(onClick = onDismissRequest) {
                            Text("Cancel")
                        }
                        TextButton(onClick = {
                            onDateSelected(selectedDate)
                            onDismissRequest()
                        }) {
                            Text("OK")
                        }
                    }
                }
            }
        }
    )
}

