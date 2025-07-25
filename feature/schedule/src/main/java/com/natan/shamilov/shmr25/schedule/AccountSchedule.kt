package com.natan.shamilov.shmr25.schedule

import android.graphics.Canvas
import android.graphics.RectF
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.github.mikephil.charting.animation.ChartAnimator
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.interfaces.dataprovider.BarDataProvider
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.github.mikephil.charting.renderer.BarChartRenderer
import com.github.mikephil.charting.utils.Utils
import com.github.mikephil.charting.utils.ViewPortHandler
import kotlin.math.ln

@Composable
fun AccountSchedule(
    modifier: Modifier = Modifier,
    expenses: Map<String, Double>,
    incomes: Map<String, Double>,
) {
    var selectedChart by remember { mutableStateOf(ChartType.Bar) }
    val alpha by animateFloatAsState(
        targetValue = 1f,
        animationSpec = tween(durationMillis = 1000)
    )

    Column(modifier = modifier.graphicsLayer { this.alpha = alpha }) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            ChartType.values().forEach { chart ->
                val isSelected = chart == selectedChart
                Button(
                    onClick = { selectedChart = chart },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isSelected) MaterialTheme.colorScheme.primaryContainer else Color.LightGray
                    ),
                    modifier = Modifier
                        .padding(horizontal = 4.dp)
                        .defaultMinSize(minWidth = 100.dp)
                ) {
                    Text(chart.name)
                }
            }
        }
        when (selectedChart) {
            ChartType.Line -> IncomeExpenseLineChart(
                expensesMap = expenses,
                incomeMap = incomes,
                modifier = Modifier.fillMaxWidth()
            )

            ChartType.Bar -> IncomeExpenseBarChart(
                expensesMap = expenses,
                incomeMap = incomes,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

// Enum для выбора типа графика
enum class ChartType {
    Bar, Line
}

@Composable
fun IncomeExpenseLineChart(
    expensesMap: Map<String, Double>,
    incomeMap: Map<String, Double>,
    modifier: Modifier = Modifier,
) {
    val expenseLineColor = Color(0xFFE57373)
    val incomeLineColor = Color(0xFF81C784)

    val sortedDates = (expensesMap.keys + incomeMap.keys).toSortedSet().toList()

    val maxValue = maxOf(
        expensesMap.values.maxOrNull() ?: 0.0,
        incomeMap.values.maxOrNull() ?: 0.0
    ).takeIf { it > 0.0 } ?: 1.0

    val logMaxValue = ln(maxValue + 1)

    fun normalize(value: Double): Float {
        return if (value == 0.0) 0f else maxOf((ln(value + 1) / logMaxValue).toFloat(), 0.05f)
    }

    fun createEntries(map: Map<String, Double>): List<Entry> {
        return sortedDates.mapIndexed { index, date ->
            Entry(index.toFloat(), normalize(map[date] ?: 0.0), date)
        }
    }

    val expenseEntries = createEntries(expensesMap)
    val incomeEntries = createEntries(incomeMap)

    val selectedEntry = remember { mutableStateOf<Triple<String, Double, Double>?>(null) }

    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        AndroidView(
            factory = { context ->
                LineChart(context).apply {
                    description.isEnabled = false
                    setDrawGridBackground(false)
                    axisRight.isEnabled = false
                    axisLeft.isEnabled = false
                    setPinchZoom(false)
                    setScaleEnabled(false)
                    animateX(1000)
                    animateY(1000)
                    legend.isEnabled = false
                    legend.apply {
                        verticalAlignment = Legend.LegendVerticalAlignment.TOP
                        horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
                        orientation = Legend.LegendOrientation.HORIZONTAL
                        setDrawInside(false)
                        textSize = 12f
                    }

                    xAxis.apply {
                        position = XAxis.XAxisPosition.BOTTOM
                        granularity = 1f
                        setDrawGridLines(false)
                        valueFormatter = IndexAxisValueFormatter(sortedDates.map { it.substring(5) })
                        textSize = 10f
                        axisMinimum = 0f
                    }

                    setOnChartValueSelectedListener(object : OnChartValueSelectedListener {
                        override fun onValueSelected(e: Entry?, h: Highlight?) {
                            if (e != null) {
                                val index = e.x.toInt()
                                val date = sortedDates.getOrNull(index) ?: return
                                val expenseSum = expensesMap[date] ?: 0.0
                                val incomeSum = incomeMap[date] ?: 0.0
                                selectedEntry.value = Triple(date, expenseSum, incomeSum)
                            } else {
                                selectedEntry.value = null
                            }
                        }

                        override fun onNothingSelected() {
                            selectedEntry.value = null
                        }
                    })
                }
            },
            update = { chart ->
                val expenseDataSet = LineDataSet(expenseEntries, "Расходы").apply {
                    color = expenseLineColor.toArgb()
                    setDrawCircles(true)
                    setCircleColor(expenseLineColor.toArgb())
                    setDrawValues(false)
                    lineWidth = 2f
                    mode = LineDataSet.Mode.LINEAR
                }

                val incomeDataSet = LineDataSet(incomeEntries, "Доходы").apply {
                    color = incomeLineColor.toArgb()
                    setDrawCircles(true)
                    setCircleColor(incomeLineColor.toArgb())
                    setDrawValues(false)
                    lineWidth = 2f
                    mode = LineDataSet.Mode.LINEAR
                }

                val lineData = LineData(expenseDataSet, incomeDataSet)
                chart.data = lineData

                // Отступы по X (впереди и в конце)
                chart.xAxis.axisMinimum = -0.5f
                chart.xAxis.axisMaximum = (sortedDates.size - 1) + 0.5f

                chart.setExtraOffsets(16f, 0f, 16f, 0f)

                chart.invalidate()
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        selectedEntry.value?.let { (date, expenseSum, incomeSum) ->
            Surface(
                color = MaterialTheme.colorScheme.surface.copy(alpha = 0.8f),
                modifier = Modifier,
                shape = RoundedCornerShape(8.dp)
            ) {
                Column {
                    Text(
                        text = date,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(text = "Расходы: ${"%.2f".format(expenseSum)}", color = expenseLineColor)
                    Text(text = "Доходы: ${"%.2f".format(incomeSum)}", color = incomeLineColor)
                }
            }
        }
    }
}

@Composable
fun IncomeExpenseBarChart(
    expensesMap: Map<String, Double>, // Double вместо Float
    incomeMap: Map<String, Double>,
    modifier: Modifier = Modifier,
) {
    val positiveColor = Color(0xFF81C784)
    val negativeColor = Color(0xFFE57373)
    val zeroValueColor = Color(0x0002CBF5)
    val minBarHeightRatio = 0.05f

    val sortedDates = (expensesMap.keys + incomeMap.keys).toSortedSet().toList()

    val maxAbsDiff = sortedDates.maxOfOrNull {
        kotlin.math.abs((incomeMap[it] ?: 0.0) - (expensesMap[it] ?: 0.0))
    }?.takeIf { it > 0.0 } ?: 1.0

    val logMaxValue = ln(maxAbsDiff + 1)

    fun normalizeDiff(value: Double): Float {
        if (value == 0.0) return 0f
        val ratio = ln(kotlin.math.abs(value) + 1) / logMaxValue
        return maxOf(ratio.toFloat(), minBarHeightRatio)
    }

    val netEntries = mutableListOf<BarEntry>()
    val barColors = mutableListOf<Int>()

    sortedDates.forEachIndexed { index, date ->
        val income = incomeMap[date] ?: 0.0
        val expense = expensesMap[date] ?: 0.0
        val diff = income - expense

        val y = if (diff == 0.0) minBarHeightRatio else normalizeDiff(diff)
        netEntries.add(BarEntry(index.toFloat(), y, Triple(date, expense, income)))

        val color = when {
            diff > 0 -> positiveColor
            diff < 0 -> negativeColor
            else -> zeroValueColor
        }
        barColors.add(color.toArgb())
    }

    val selectedEntry = remember { mutableStateOf<Triple<String, Double, Double>?>(null) }

    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        AndroidView(
            factory = { context ->
                BarChart(context).apply {
                    description.isEnabled = false
                    setDrawGridBackground(false)
                    axisRight.isEnabled = false
                    axisLeft.isEnabled = false
                    legend.isEnabled = false

                    xAxis.apply {
                        position = XAxis.XAxisPosition.BOTTOM
                        granularity = 1f
                        setDrawGridLines(false)
                        valueFormatter = IndexAxisValueFormatter(sortedDates.map { it.substring(5) })
                        textSize = 10f
                        axisMinimum = 0f
                    }

                    animateX(1000)
                    animateY(1000)
                    setFitBars(true)
                    setPinchZoom(false)
                    setScaleEnabled(false)
                    setExtraOffsets(16f, 0f, 16f, 0f)
                    renderer = RoundedBarChartRenderer(this, animator, viewPortHandler)

                    setOnChartValueSelectedListener(object : OnChartValueSelectedListener {
                        override fun onValueSelected(e: Entry?, h: Highlight?) {
                            if (e is BarEntry) {
                                selectedEntry.value = e.data as? Triple<String, Double, Double>
                            } else {
                                selectedEntry.value = null
                            }
                        }

                        override fun onNothingSelected() {
                            selectedEntry.value = null
                        }
                    })
                }
            },
            update = { chart ->
                val dataSet = BarDataSet(netEntries, "Разница").apply {
                    setColors(barColors)
                    setDrawValues(false)
                }

                chart.data = BarData(dataSet).apply {
                    barWidth = 0.5f
                }

                chart.xAxis.axisMinimum = -0.5f
                chart.xAxis.axisMaximum = sortedDates.size - 0.5f

                chart.invalidate()
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        selectedEntry.value?.let { (date, expenseSum, incomeSum) ->
            Surface(
                color = MaterialTheme.colorScheme.surface.copy(alpha = 0.8f),
                modifier = Modifier,
                shape = RoundedCornerShape(8.dp)
            ) {
                Column {
                    Text(
                        text = date,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(text = "Расходы: ${"%.2f".format(expenseSum)}", color = negativeColor)
                    Text(text = "Доходы: ${"%.2f".format(incomeSum)}", color = positiveColor)
                }
            }
        }
    }
}

@Composable
fun LegendItem(color: Color, label: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(12.dp)
                .background(color, shape = CircleShape)
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall
        )
    }
}

class RoundedBarChartRenderer(
    chart: BarDataProvider,
    animator: ChartAnimator,
    viewPortHandler: ViewPortHandler,
) : BarChartRenderer(chart, animator, viewPortHandler) {

    private val barRadius = 50f

    override fun drawDataSet(c: Canvas, dataSet: IBarDataSet, index: Int) {
        val trans = mChart.getTransformer(dataSet.axisDependency)

        mBarBorderPaint.color = dataSet.barBorderColor
        mBarBorderPaint.strokeWidth = Utils.convertDpToPixel(dataSet.barBorderWidth)

        val drawBorder = dataSet.barBorderWidth > 0f

        val phaseX = mAnimator.phaseX
        val phaseY = mAnimator.phaseY

        if (mBarBuffers.size < index + 1) return

        val buffer = mBarBuffers[index]
        buffer.setPhases(phaseX, phaseY)
        buffer.setDataSet(index)
        buffer.setInverted(mChart.isInverted(dataSet.axisDependency))
        buffer.setBarWidth(mChart.barData.barWidth)

        buffer.feed(dataSet)

        trans.pointValuesToPixel(buffer.buffer)

        val isSingleColor = dataSet.colors.size == 1

        for (j in buffer.buffer.indices step 4) {
            if (!mViewPortHandler.isInBoundsLeft(buffer.buffer[j + 2])) continue

            if (!mViewPortHandler.isInBoundsRight(buffer.buffer[j])) break

            val left = buffer.buffer[j]
            val top = buffer.buffer[j + 1]
            val right = buffer.buffer[j + 2]
            val bottom = buffer.buffer[j + 3]

            mRenderPaint.color = if (isSingleColor) dataSet.color else dataSet.getColor(j / 4)

            val rectF = RectF(left, top, right, bottom)

            c.drawRoundRect(rectF, barRadius, barRadius, mRenderPaint)

            if (drawBorder) {
                c.drawRoundRect(rectF, barRadius, barRadius, mBarBorderPaint)
            }
        }
    }
}
