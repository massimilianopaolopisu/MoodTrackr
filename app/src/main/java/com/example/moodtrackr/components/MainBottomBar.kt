package com.example.moodtrackr.components

import android.os.Process
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.example.moodtrackr.R
import com.example.moodtrackr.enums.Routes

@Composable
fun MainBottomBar(
    navController: NavController
){
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Default.DateRange,
            contentDescription = "Mood Entries History",
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .clickable {
                    navController.navigate(Routes.MoodEntriesHistory.toString())
                }
                .align(Alignment.CenterVertically)
                .weight(1f)
        )
        Icon(
            painterResource(id = R.drawable.ic_graph),
            contentDescription = "Graphs",
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .clickable {
                    navController.navigate(Routes.MoodEntriesHistory.toString())
                }
                .align(Alignment.CenterVertically)
                .weight(1f)
        )
        Icon(
            imageVector = Icons.Default.ExitToApp,
            contentDescription = "Exit",
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .clickable {
                    Process.killProcess(Process.myPid())
                }
                .align(Alignment.CenterVertically)
                .weight(1f)
        )
        Icon(
            painterResource(id = R.drawable.ic_chart),
            contentDescription = "Statistics",
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .clickable {
                    navController.navigate(Routes.MoodEntriesHistory.toString())
                }
                .align(Alignment.CenterVertically)
                .weight(1f)
        )
        Icon(
            imageVector = Icons.Default.Settings,
            contentDescription = "Settings",
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .clickable {
                    navController.navigate(Routes.Settings.toString())
                }
                .align(Alignment.CenterVertically)
                .weight(1f)
        )
    }
}