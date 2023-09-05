package com.example.moodtrackr.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.moodtrackr.enums.Routes
import com.example.moodtrackr.repositories.interfaces.ISave

@Composable
fun SaveBottomBar(
    navController: NavController,
    saveHandlerAndObjectPairList: List<Pair<ISave<Any>, Any>>,
    saveLabel: String = "Save",
    backLabel: String = "Back",
    afterSaveRoute: Routes? = Routes.Home,
    afterBackRoute: Routes? = Routes.Home
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Button(
            onClick = {
                if(afterSaveRoute != null){
                    navController.navigate(afterBackRoute.toString())
                }
                else {
                    navController.popBackStack()
                }
            },
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            Text(backLabel)
        }

        Button(
            onClick = {
                saveHandlerAndObjectPairList.forEach{ (saveHandler, obj) ->
                    saveHandler.save(obj)
                    if(afterSaveRoute != null){
                        navController.navigate(afterSaveRoute.toString())
                    }
                }
            },
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            Text(saveLabel)
        }
    }
}