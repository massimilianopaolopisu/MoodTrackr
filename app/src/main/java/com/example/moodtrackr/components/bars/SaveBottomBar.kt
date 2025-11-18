package com.example.moodtrackr.components.bars

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
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
import com.example.moodtrackr.helpers.ToastNotificationHelper
import com.example.moodtrackr.repositories.interfaces.ISave
import com.example.moodtrackr.viewModels.MainViewModel
import java.time.LocalDate
import com.example.moodtrackr.extensions.navigateTo

@Composable
fun SaveBottomBar(
    navController: NavController,
    viewModel: MainViewModel,
    saveHandlerAndObjectPairList: List<Pair<ISave<Any>, Any>>,
    afterSaveRoute: String?) {

    val homeRoute = "${Routes.Home}/${ LocalDate.now()}"

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        val icons = listOf(
            Triple(
                Icons.Default.Home,
                "Home"
            ) {
                navController.navigateTo(homeRoute)
            },
            Triple(
                Icons.Default.Done,
                "Save"
            ) {
                saveHandlerAndObjectPairList.forEach { (saveHandler, obj) ->
                    saveHandler.save(obj)
                }
                ToastNotificationHelper.showShortToastNotification(viewModel.mainActivity, "Saved")

                afterSaveRoute?.let {
                    navController.navigateTo(it)
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