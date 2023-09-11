package com.example.moodtrackr.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.moodtrackr.enums.Routes
import com.example.moodtrackr.repositories.interfaces.ISave

@Composable
fun SaveBottomBar(
    navController: NavController,
    saveHandlerAndObjectPairList: List<Pair<ISave<Any>, Any>>,
    afterSaveRoute: Routes? = Routes.Home) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        val icons = listOf(
            Triple(
                Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                "Back"
            ) {
                navController.popBackStack()
            },
            Triple(
                Icons.Default.Home,
                "Home"
            ) {
                navController.navigate(Routes.Home.toString())
            },
            Triple(
                Icons.AutoMirrored.Filled.ExitToApp,
                "Exit"
            ) {
                android.os.Process.killProcess(android.os.Process.myPid())
            },
            Triple(
                Icons.Default.Done,
                "Save"
            ) {
                saveHandlerAndObjectPairList.forEach { (saveHandler, obj) ->
                    saveHandler.save(obj)
                }
                afterSaveRoute?.let {
                    navController.navigate(it.toString())
                }
            }
        )

        for ((icon, contentDescription, onClick) in icons) {
            Icon(
                imageVector = icon,
                contentDescription = contentDescription,
                tint = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .clickable {
                        onClick.invoke()
                    }
                    .align(Alignment.CenterVertically)
                    .weight(1f)
            )
        }
    }
}