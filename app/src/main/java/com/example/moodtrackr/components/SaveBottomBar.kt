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
import com.example.moodtrackr.repositories.ISave

@Composable
fun SaveBottomBar(
    navController: NavController,
    saveHandlerAndObjectPairList: List<Pair<ISave<Any>, Any>>
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Button(
            onClick = {
                navController.navigate("home")
            },
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            Text("Back")
        }

        Button(
            onClick = {
                saveHandlerAndObjectPairList.forEach{ (saveHandler, obj) ->
                    saveHandler.save(obj)
                    navController.navigate("home")
                }
            },
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            Text("Save")
        }
    }
}