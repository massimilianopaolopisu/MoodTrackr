package com.example.moodtrackr.components.bars

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.moodtrackr.enums.Routes
import com.example.moodtrackr.extensions.navigateTo
import com.example.moodtrackr.utilities.DateUtilities
import java.time.LocalDate

@Composable
fun DateBar(
    navController: NavController,
    localDate: LocalDate,
    origin: String?,
    showButtons: Boolean
){
    val editRoute = "${Routes.EditMoodEntry}/$localDate"

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (showButtons) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                contentDescription = "Back",
                tint = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.clickable {
                    val date = localDate.minusDays(1)
                    navController.navigateTo("$origin/$date")
                }
            )
        }

        Text(
            text = getDataLabel(localDate),
            style = MaterialTheme.typography.bodyLarge.copy(fontSize = 26.sp),
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier
                .weight(2f)
                .clickable {
                    navController.navigateTo(editRoute)
                },
            textAlign = TextAlign.Center
        )

        if(showButtons) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = "Next",
                tint = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .clickable {
                        val date = localDate.plusDays(1)
                        navController.navigateTo("$origin/$date")
                }
            )
        }
    }
}

fun getDataLabel(date: LocalDate): String {
    val today = LocalDate.now()
    val yesterday = today.minusDays(1)

    return when (date) {
        today -> "Today"
        yesterday -> "Yesterday"
        else -> DateUtilities.getStringDateFromLocalDate(date)
    }
}