package com.example.moodtrackr.screens

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.moodtrackr.models.SharedPreferencesKeys

@Composable
fun HomeScreen(navController: NavController) {
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences(SharedPreferencesKeys.PROFILE, Context.MODE_PRIVATE)
    val name = getName(sharedPreferences)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Hello $name",
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                textAlign = TextAlign.Center
            ),
            modifier = Modifier.padding(vertical = 8.dp)
        )

        Button(
            onClick = { navController.navigate("editProfile") },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            Text("Edit Profile")
        }
    }
}

fun getName(sharedPreferences: SharedPreferences): String {
    val savedName = sharedPreferences.getString("name", "")
    var name = savedName

    if (name?.isBlank() == true || name?.isBlank() == null)
        name = "user"

    return name
}