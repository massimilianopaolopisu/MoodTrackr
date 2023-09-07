package com.example.moodtrackr.models

data class IndicatorStats(
    val min: IndicatorValue?,
    val average: Int?,
    val max: IndicatorValue?,
    val indicatorName: String?
)