package com.example.moodtrackr.components

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.viewinterop.AndroidView
import com.example.moodtrackr.logic.formatters.DateValueFormatter
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.LineData

@Composable
fun LineChart(
    modifier: Modifier = Modifier,
    data: LineData,
    description: String? = null,
    backgroundColor: Color = Color.Transparent,
    legendEnabled: Boolean = false,
    xAxisPosition: XAxis.XAxisPosition = XAxis.XAxisPosition.BOTTOM,
    yAxisMinimum: Float = 0f,
    yAxisEnabled: Boolean = false,
    xAxisConvertMillisToDate: Boolean = false,
    xAxisLabelCount: Int = 4
) {
    val chart = rememberUpdatedState(data)

    AndroidView(
        modifier = modifier.fillMaxWidth().fillMaxHeight(),
        factory = { context ->
            com.github.mikephil.charting.charts.LineChart(context)
        },
        update = { chartView ->
            chartView.data = chart.value
            chartView.description.text = description
            chartView.setBackgroundColor(backgroundColor.toArgb())

            val legend = chartView.legend
            legend.isEnabled = legendEnabled

            val xAxis = chartView.xAxis
            xAxis.position = xAxisPosition
            xAxis.labelCount = xAxisLabelCount

            if (xAxisConvertMillisToDate) {
                xAxis.valueFormatter = DateValueFormatter()
            }

            val leftYAxis = chartView.axisLeft
            leftYAxis.axisMinimum = yAxisMinimum

            chartView.axisRight.isEnabled = yAxisEnabled
        }
    )
}