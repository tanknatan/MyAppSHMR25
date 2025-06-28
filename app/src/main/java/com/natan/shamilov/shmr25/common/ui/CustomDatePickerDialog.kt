package com.natan.shamilov.shmr25.common.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.BasicAlertDialog
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

/**
 * Кастомный диалог выбора даты
 *
 * @param onDismissRequest Callback закрытия диалога
 * @param onDateSelected Callback выбора даты
 * @param onClear Callback очистки выбранной даты
 * @param initialDate Начальная дата в миллисекундах
 * @param isStartDatePicker Флаг, указывающий выбор начальной даты периода
 */
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

    BasicAlertDialog(
        onDismissRequest = onDismissRequest,
        content = {
            Column(
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color(0xFFE6FFF2))
            ) {
                DatePickerHeader(
                    displayedMonth = displayedMonth,
                    displayedYear = displayedYear,
                    onMonthChanged = { displayedMonth = it },
                    onYearChanged = { displayedYear = it }
                )

                DayGrid(
                    displayedMonth = displayedMonth,
                    displayedYear = displayedYear,
                    selectedDate = selectedDate,
                    onDateSelected = { newDate ->
                        selectedDate = newDate
                    }
                )

                DatePickerActions(
                    onClear = {
                        onClear()
                        onDismissRequest()
                    },
                    onCancel = onDismissRequest,
                    onConfirm = {
                        onDateSelected(selectedDate)
                        onDismissRequest()
                    }
                )
            }
        }
    )
}

/**
 * Шапка диалога с выбором месяца и года
 */
@Composable
private fun DatePickerHeader(
    displayedMonth: Int,
    displayedYear: Int,
    onMonthChanged: (Int) -> Unit,
    onYearChanged: (Int) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        MonthSelector(
            currentMonth = displayedMonth,
            onMonthSelected = onMonthChanged,
            onYearChanged = onYearChanged
        )

        YearSelector(
            currentYear = displayedYear,
            onYearSelected = onYearChanged
        )
    }
}

/**
 * Компонент выбора месяца
 */
@Composable
private fun MonthSelector(
    currentMonth: Int,
    onMonthSelected: (Int) -> Unit,
    onYearChanged: (Int) -> Unit
) {
    var showMonthDropdown by remember { mutableStateOf(false) }
    val monthNames = Month.entries.map { it.getDisplayName(TextStyle.SHORT, Locale.getDefault()) }

    Row(verticalAlignment = Alignment.CenterVertically) {
        IconButton(onClick = {
            if (currentMonth == 0) {
                onMonthSelected(11)
                onYearChanged(-1)
            } else {
                onMonthSelected(currentMonth - 1)
            }
        }) {
            Icon(Icons.Default.KeyboardArrowLeft, "Previous month")
        }

        Box {
            TextButton(onClick = { showMonthDropdown = true }) {
                Text(monthNames[currentMonth])
                Icon(Icons.Default.ArrowDropDown, null)
            }
            DropdownMenu(
                expanded = showMonthDropdown,
                onDismissRequest = { showMonthDropdown = false }
            ) {
                monthNames.forEachIndexed { index, name ->
                    DropdownMenuItem(
                        text = { Text(name) },
                        onClick = {
                            onMonthSelected(index)
                            showMonthDropdown = false
                        }
                    )
                }
            }
        }

        IconButton(onClick = {
            if (currentMonth == 11) {
                onMonthSelected(0)
                onYearChanged(1)
            } else {
                onMonthSelected(currentMonth + 1)
            }
        }) {
            Icon(Icons.Default.KeyboardArrowRight, "Next month")
        }
    }
}

/**
 * Компонент выбора года
 */
@Composable
private fun YearSelector(
    currentYear: Int,
    onYearSelected: (Int) -> Unit
) {
    var showYearDropdown by remember { mutableStateOf(false) }
    val yearRange = (2000..2100).toList()

    Row(verticalAlignment = Alignment.CenterVertically) {
        IconButton(onClick = { onYearSelected(currentYear - 1) }) {
            Icon(Icons.Default.KeyboardArrowLeft, "Previous year")
        }

        Box {
            TextButton(onClick = { showYearDropdown = true }) {
                Text(currentYear.toString())
                Icon(Icons.Default.ArrowDropDown, null)
            }

            DropdownMenu(
                expanded = showYearDropdown,
                onDismissRequest = { showYearDropdown = false }
            ) {
                yearRange.forEach { year ->
                    DropdownMenuItem(
                        text = { Text(year.toString()) },
                        onClick = {
                            onYearSelected(year)
                            showYearDropdown = false
                        }
                    )
                }
            }
        }

        IconButton(onClick = { onYearSelected(currentYear + 1) }) {
            Icon(Icons.Default.KeyboardArrowRight, "Next year")
        }
    }
}

/**
 * Сетка дней месяца
 */
@Composable
private fun DayGrid(
    displayedMonth: Int,
    displayedYear: Int,
    selectedDate: Long,
    onDateSelected: (Long) -> Unit
) {
    val daysInMonth = remember(displayedMonth, displayedYear) {
        Calendar.getInstance().apply {
            set(Calendar.YEAR, displayedYear)
            set(Calendar.MONTH, displayedMonth)
            set(Calendar.DAY_OF_MONTH, 1)
        }.getActualMaximum(Calendar.DAY_OF_MONTH)
    }

    val firstDayOffset = remember(displayedMonth, displayedYear) {
        Calendar.getInstance().apply {
            set(Calendar.YEAR, displayedYear)
            set(Calendar.MONTH, displayedMonth)
            set(Calendar.DAY_OF_MONTH, 1)
        }.let {
            val dayOfWeek = it.get(Calendar.DAY_OF_WEEK)
            (dayOfWeek + 5) % 7
        }
    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(7),
        modifier = Modifier.height(300.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        items(7) { index -> DayHeader(dayIndex = index) }
        items(firstDayOffset) { EmptyDayCell() }
        items(daysInMonth) { index ->
            DayCell(
                day = index + 1,
                isSelected = isSelectedDay(
                    day = index + 1,
                    month = displayedMonth,
                    year = displayedYear,
                    selectedDate = selectedDate
                ),
                onClick = {
                    val newDate = Calendar.getInstance().apply {
                        set(Calendar.YEAR, displayedYear)
                        set(Calendar.MONTH, displayedMonth)
                        set(Calendar.DAY_OF_MONTH, index + 1)
                    }.timeInMillis
                    onDateSelected(newDate)
                }
            )
        }
    }
}

/**
 * Заголовок дня недели
 */
@Composable
private fun DayHeader(dayIndex: Int) {
    val dayHeaders = listOf("S", "M", "T", "W", "T", "F", "S")
    Box(
        modifier = Modifier.size(40.dp).aspectRatio(1f),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = dayHeaders[dayIndex],
            style = MaterialTheme.typography.bodySmall.copy(
                fontWeight = FontWeight.SemiBold
            ),
            color = Color.Gray
        )
    }
}

/**
 * Пустая ячейка дня
 */
@Composable
private fun EmptyDayCell() {
    Box(modifier = Modifier.size(40.dp).aspectRatio(1f))
}

/**
 * Ячейка дня
 */
@Composable
private fun DayCell(
    day: Int,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(40.dp)
            .aspectRatio(1f)
            .clip(CircleShape)
            .background(if (isSelected) Color(0xFF00C853) else Color.Transparent)
            .clickable(onClick = onClick)
    ) {
        Text(
            text = day.toString(),
            color = if (isSelected) Color.White else Color.Black
        )
    }
}

/**
 * Проверяет, является ли день выбранным
 */
private fun isSelectedDay(
    day: Int,
    month: Int,
    year: Int,
    selectedDate: Long
): Boolean {
    val calendar = Calendar.getInstance().apply { timeInMillis = selectedDate }
    return calendar.get(Calendar.DAY_OF_MONTH) == day &&
        calendar.get(Calendar.MONTH) == month &&
        calendar.get(Calendar.YEAR) == year
}

/**
 * Кнопки управления в нижней части диалога
 */
@Composable
private fun DatePickerActions(
    onClear: () -> Unit,
    onCancel: () -> Unit,
    onConfirm: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        TextButton(onClick = onClear) {
            Text("Clear")
        }

        Row {
            TextButton(onClick = onCancel) {
                Text("Cancel")
            }
            TextButton(onClick = onConfirm) {
                Text("OK")
            }
        }
    }
}
