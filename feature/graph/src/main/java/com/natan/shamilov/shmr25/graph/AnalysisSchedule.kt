package com.natan.shamilov.shmr25.graph

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import kotlinx.coroutines.delay

@Composable
fun AnalysisSchedule(
    modifier: Modifier = Modifier,
    data: Map<String, Float>,
    colors: List<Color>,
) {
    val context = LocalContext.current
    val selectedEntry = remember { mutableStateOf<PieEntry?>(null) }
    var curentdata by remember { mutableStateOf(data) }
    var curentcolor by remember { mutableStateOf(colors) }

    if (data.isEmpty()) {
        curentdata = mapOf("No data" to 100f)
        curentcolor = listOf(Color.Gray)
    }

    var visible by remember { mutableStateOf(false) }

    // Плавное появление (alpha) с анимацией
    val alpha by animateFloatAsState(
        targetValue = if (visible) 1f else 0f,
        animationSpec = tween(durationMillis = 600),
        label = "graph-fade"
    )
    val hasAnimated = remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        delay(100)
        visible = true
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(220.dp) // всегда резервируем место
    ) {
        AndroidView(
            factory = {
                PieChart(context).apply {
                    description.isEnabled = false
                    setUsePercentValues(true)
                    setDrawHoleEnabled(true)
                    holeRadius = 80f
                    transparentCircleRadius = 75f
                    setEntryLabelColor(Color.Black.hashCode())
                    setEntryLabelTextSize(0f)
                    legend.isEnabled = false

                    setOnChartValueSelectedListener(object : OnChartValueSelectedListener {
                        override fun onValueSelected(e: Entry?, h: Highlight?) {
                            val pieEntry = e as? PieEntry
                            selectedEntry.value = pieEntry
                            centerText = pieEntry?.let {
                                "${it.label}\n${"%.1f".format(it.value)}%"
                            } ?: ""
                        }

                        override fun onNothingSelected() {
                            selectedEntry.value = null
                            centerText = ""
                        }
                    })
                }
            },
            update = { pieChart ->
                val entries = curentdata.map { PieEntry(it.value, it.key) }
                val dataSet = PieDataSet(entries, "").apply {
                    this.colors = curentcolor.map { it.toArgb() }
                    valueTextSize = 0f
                    sliceSpace = 3f
                    selectionShift = 10f
                }

                val pieData = PieData(dataSet).apply {
                    setValueFormatter(PercentFormatter(pieChart))
                }

                pieChart.data = pieData

                selectedEntry.value?.let { entry ->
                    pieChart.centerText = "${entry.label}\n${"%.1f".format(entry.value)}%"
                }

                if (!hasAnimated.value) {
                    pieChart.animateY(800, Easing.EaseInOutQuad)
                    hasAnimated.value = true
                }

                pieChart.invalidate()
            },
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer {
                    this.alpha = alpha
                }
        )
    }
}

