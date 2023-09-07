package com.example.moodtrackr.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.moodtrackr.utilities.DateUtilities
import java.time.LocalDate

@Composable
fun DateBar(
    navController: NavController,
    localDate: LocalDate,
    origin: String?,
    showButtons: Boolean
){
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (showButtons) {
            Icon(
                imageVector = Icons.Default.KeyboardArrowLeft,
                contentDescription = "Back",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.clickable {
                    val date = localDate.minusDays(1)
                    navController.navigate("$origin/$date")
                }
            )
        }

        Text(
            text = DateUtilities.getStringDateFromLocalDate(localDate),
            style = MaterialTheme.typography.bodyLarge.copy(fontSize = 26.sp),
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center
        )

        if(showButtons) {
            Icon(
                imageVector = Icons.Default.KeyboardArrowRight,
                contentDescription = "Next",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.clickable {
                    val date = localDate.plusDays(1)
                    navController.navigate("$origin/$date")
                }
            )
        }
    }
}