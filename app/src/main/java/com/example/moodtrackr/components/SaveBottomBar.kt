package com.example.moodtrackr.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.KeyboardArrowLeft
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
    afterSaveRoute: Routes? = Routes.Home,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Icon(
            imageVector = Icons.Default.KeyboardArrowLeft,
            contentDescription = "Back",
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .clickable {
                    navController.popBackStack()
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
                    android.os.Process.killProcess(android.os.Process.myPid())
                }
                .align(Alignment.CenterVertically)
                .weight(1f)
        )
        Icon(
            imageVector = Icons.Default.Home,
            contentDescription = "Home",
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .clickable {
                    navController.navigate(Routes.Home.toString())
                }
                .align(Alignment.CenterVertically)
                .weight(1f)
        )
        Icon(
            imageVector = Icons.Default.Done,
            contentDescription = "Save",
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .clickable {
                    saveHandlerAndObjectPairList.forEach{ (saveHandler, obj) ->
                        saveHandler.save(obj)
                        if(afterSaveRoute != null){
                            navController.navigate(afterSaveRoute.toString())
                        }
                    }
                }
                .align(Alignment.CenterVertically)
                .weight(1f)
        )
    }
}