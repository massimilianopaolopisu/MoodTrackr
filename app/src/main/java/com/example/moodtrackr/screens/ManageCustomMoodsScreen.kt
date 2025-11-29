package com.example.moodtrackr.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.moodtrackr.components.bars.MainBottomBar
import com.example.moodtrackr.components.bars.TitleTopBar
import com.example.moodtrackr.helpers.ActivityHelper
import com.example.moodtrackr.models.CustomMood
import com.example.moodtrackr.viewModels.MainViewModel

@Composable
fun ManageCustomMoodsScreen(
    navController: NavController,
    viewModel: MainViewModel
) {
    var customMoods by remember { mutableStateOf(viewModel.customMoodsRepository.getAllCustomMoods()) }
    var showAddDialog by remember { mutableStateOf(false) }
    var newMoodName by remember { mutableStateOf("") }
    var newMoodColor by remember { mutableStateOf(Color.Blue.value.toLong()) }
    var moodToDelete by remember { mutableStateOf<CustomMood?>(null) }
    var editingMood by remember { mutableStateOf<CustomMood?>(null) }

    val focusManager = LocalFocusManager.current
    val focusRequester = FocusRequester()

    val colorOptions = listOf(
        Color.Red, Color.Blue, Color.Green, Color.Yellow,
        Color.Magenta, Color.Cyan, Color(0xFFFFA500),
        Color(0xFF800080),
        Color(0xFFFFC0CB),
        Color(0xFF00CED1)
    )

    ActivityHelper.resetWindowBackground(viewModel.mainActivity)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.TopCenter)
        ) {
            TitleTopBar(navController, "Manage Custom Moods")
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 25.dp)
                    .padding(bottom = 100.dp)
                    .weight(1f)
            ) {
                item {
                    if (customMoods.isEmpty()) {
                        Text(
                            text = "No custom moods yet. Tap the button below to add one.",
                            color = MaterialTheme.colorScheme.onBackground,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }

                items(customMoods) { mood ->
                    CustomMoodCard(
                        mood = mood,
                        onDelete = { moodToDelete = mood },
                        onEdit = {
                            editingMood = mood
                            showAddDialog = true
                            newMoodName = mood.name
                            newMoodColor = mood.colorValue
                        }
                    )
                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
        ) {
            MainBottomBar(navController)
        }

        ExtendedFloatingActionButton(
            text = { Text("Add Custom Mood") },
            icon = {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add custom mood"
                )
            },
            onClick = {
                editingMood = null
                showAddDialog = true
            },
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 88.dp)
        )
    }

    if (showAddDialog) {
        val keyboardController = LocalSoftwareKeyboardController.current

        AlertDialog(
            onDismissRequest = {
                showAddDialog = false
                newMoodName = ""
                newMoodColor = Color.Blue.value.toLong()
                focusManager.clearFocus()
            },
            title = { Text(text = "Create New Mood") },
            text = {
                Column {
                    OutlinedTextField(
                        value = newMoodName,
                        onValueChange = { newMoodName = it },
                        label = { Text("Mood name") },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 12.dp)
                            .focusRequester(focusRequester),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(onDone = {
                            focusManager.clearFocus()
                            keyboardController?.hide()
                        })
                    )

                    Text(
                        text = "Choose Color",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier
                            .align(Alignment.Start)
                            .padding(bottom = 8.dp)
                    )

                    Column(modifier = Modifier.fillMaxWidth()) {
                        for (i in colorOptions.indices step 5) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 8.dp),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                for (j in 0 until minOf(5, colorOptions.size - i)) {
                                    Box(
                                        modifier = Modifier
                                            .size(50.dp)
                                            .background(
                                                colorOptions[i + j],
                                                RoundedCornerShape(8.dp)
                                            )
                                            .clickable {
                                                newMoodColor = (colorOptions[i + j].value.toLong() or 0xFF000000L)
                                            },
                                        contentAlignment = Alignment.Center
                                    ) {
                                        if (newMoodColor == (colorOptions[i + j].value.toLong() or 0xFF000000L)) {
                                            Text(
                                                "✓",
                                                color = Color.White,
                                                style = MaterialTheme.typography.headlineSmall
                                            )
                                        }
                                    }
                                }
                                repeat(maxOf(0, 5 - minOf(5, colorOptions.size - i))) {
                                    Box(modifier = Modifier.size(50.dp))
                                }
                            }
                        }
                    }
                }
            },
            confirmButton = {
                Button(onClick = {
                    if (newMoodName.isNotBlank()) {
                        val colorVal = (newMoodColor or 0xFF000000L)

                        if (editingMood != null) {
                            val updated = try {
                                viewModel.customMoodsRepository.updateCustomMood(
                                    CustomMood(
                                        id = editingMood!!.id,
                                        name = newMoodName,
                                        colorValue = colorVal
                                    )
                                )
                            } catch (ex: Exception) { null }

                            if (updated != null) {
                                customMoods = viewModel.customMoodsRepository.getAllCustomMoods()
                                Toast.makeText(viewModel.context, "Mood updated", Toast.LENGTH_SHORT).show()
                                showAddDialog = false
                                editingMood = null
                                newMoodName = ""
                                newMoodColor = Color.Blue.value.toLong()
                                focusManager.clearFocus()
                                keyboardController?.hide()
                            } else {
                                Toast.makeText(viewModel.context, "Failed to update mood", Toast.LENGTH_LONG).show()
                            }
                        } else {
                            val customMood = CustomMood(
                                id = 0,
                                name = newMoodName,
                                colorValue = colorVal
                            )
                            val inserted = try {
                                viewModel.customMoodsRepository.insertCustomMood(customMood)
                            } catch (ex: Exception) {
                                null
                            }

                            if (inserted != null) {
                                customMoods = viewModel.customMoodsRepository.getAllCustomMoods()
                                Toast.makeText(viewModel.context, "Mood saved", Toast.LENGTH_SHORT).show()
                                showAddDialog = false
                                newMoodName = ""
                                newMoodColor = Color.Blue.value.toLong()
                                focusManager.clearFocus()
                                keyboardController?.hide()
                            } else {
                                Toast.makeText(viewModel.context, "Failed to save mood", Toast.LENGTH_LONG).show()
                            }
                        }
                    } else {
                        Toast.makeText(viewModel.context, "Please enter a mood name", Toast.LENGTH_SHORT).show()
                    }
                }) { Text(if (editingMood != null) "Save" else "Create") }
            },
            dismissButton = {
                Button(onClick = {
                    showAddDialog = false
                    newMoodName = ""
                    newMoodColor = Color.Blue.value.toLong()
                    focusManager.clearFocus()
                    keyboardController?.hide()
                }) { Text("Cancel") }
            }
        )

        LaunchedEffect(showAddDialog) {
            if (showAddDialog) {
                focusRequester.requestFocus()
                keyboardController?.show()
            }
        }
    }

    if (moodToDelete != null) {
        val toDelete = moodToDelete
        AlertDialog(
            onDismissRequest = { moodToDelete = null },
            title = { Text("Delete Custom Mood") },
            text = { Text("Are you sure you want to delete '${toDelete?.name}'? This will also remove any saved values for this mood.") },
            confirmButton = {
                Button(onClick = {
                    toDelete?.let {
                        try {
                            viewModel.customMoodsRepository.deleteCustomMood(it.id)
                        } catch (_: Exception) {}
                    }
                    customMoods = viewModel.customMoodsRepository.getAllCustomMoods()
                    moodToDelete = null
                }) { Text("Delete") }
            },
            dismissButton = {
                Button(onClick = { moodToDelete = null }) { Text("Cancel") }
            }
        )
    }
}

@Composable
fun CustomMoodCard(
    mood: CustomMood,
    onDelete: () -> Unit,
    onEdit: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(mood.color, RoundedCornerShape(8.dp))
                        .clickable { onEdit() }
                )
                Text(
                    text = mood.name,
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.Edit,
                    contentDescription = "Edit mood",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .clickable { onEdit() }
                        .padding(8.dp)
                )

                Icon(
                    imageVector = Icons.Filled.Delete,
                    contentDescription = "Delete mood",
                    tint = MaterialTheme.colorScheme.error,
                    modifier = Modifier
                        .clickable { onDelete() }
                        .padding(8.dp)
                )
            }
        }
    }
}
