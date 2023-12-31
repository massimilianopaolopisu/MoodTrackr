package com.example.moodtrackr.components.bars

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.navigation.NavController
import com.example.moodtrackr.R
import com.example.moodtrackr.enums.Routes
import java.time.LocalDate

@Composable
fun MainBottomBar(
    navController: NavController
){
    val icons = listOf(
        Triple(
            Icons.Default.DateRange,
            "Mood Entries History",
            Routes.MoodEntriesHistory.toString()
        ),
        Triple(
            Icons.Default.Home,
            "Home",
            "${Routes.Home}/${LocalDate.now()}"
        ),
        Triple(
            ImageVector.vectorResource(id = R.drawable.ic_graph),
            "Graphs",
            Routes.Graphs.toString()
        ),
        Triple(
            ImageVector.vectorResource(id = R.drawable.ic_chart),
            "Statistics",
            Routes.Statistics.toString()
        )
    )

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        for ((icon, contentDescription, route) in icons) {
            val imageVector = icon as? ImageVector

            if (imageVector != null) {
                Icon(
                    imageVector = imageVector,
                    contentDescription = contentDescription,
                    tint = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier
                        .clickable {
                            navController.navigate(route)
                        }
                        .align(Alignment.CenterVertically)
                        .weight(1f)
                )
            }
        }
    }
}