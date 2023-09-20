package com.example.moodtrackr.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.moodtrackr.components.bars.MainBottomBar
import com.example.moodtrackr.components.bars.TitleTopBar
import com.example.moodtrackr.helpers.ActivityHelper
import com.example.moodtrackr.models.Profile
import com.example.moodtrackr.utilities.DateUtilities
import com.example.moodtrackr.viewModels.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(
    navController: NavController,
    viewModel: MainViewModel
) {
    var newName by remember { mutableStateOf(viewModel.profile.name) }
    var newSurname by remember { mutableStateOf(viewModel.profile.surname) }
    var newSex by remember { mutableStateOf(viewModel.profile.sex) }
    val birthday by remember { mutableStateOf(viewModel.profile.birthday) }

    val datePicker = rememberDatePickerState(DateUtilities.getMillisFromLocalDate(birthday))

    val focusRequester = FocusRequester()
    val focusManager = LocalFocusManager.current

    fun saveProfile() {
        val newBirthday = DateUtilities.getLocalDateFromMillis(datePicker.selectedDateMillis?: 0)
        viewModel.profile = Profile(newName, newSurname, newSex, newBirthday)
        viewModel.profilePreferencesRepository.save(viewModel.profile)
    }

    ActivityHelper.resetWindowBackground(viewModel.mainActivity)

    LaunchedEffect(newSex, datePicker.selectedDateMillis) {
        saveProfile()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
        ) {
            TitleTopBar(navController, "Edit Profile")
        }

        LazyColumn (
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .align(Alignment.TopCenter)
                .padding(top = 25.dp)
                .padding(bottom = 45.dp)
        ) {
            item {
                Text(
                    text = "Name",
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier
                        .padding(top = 8.dp)
                )
                BasicTextField(
                    value = newName,
                    onValueChange = { newValue: String -> newName = newValue },
                    modifier = Modifier
                        .fillMaxWidth()
                        .onFocusChanged { focusState ->
                            if (!focusState.isFocused) {
                                saveProfile()
                            }
                        },
                    textStyle = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.onBackground
                    ),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = { focusRequester.requestFocus() },
                        onDone = {
                            focusManager.clearFocus()
                            saveProfile()
                        }
                    ),
                    singleLine = true
                )

                Text(
                    text = "Surname",
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier
                        .padding(top = 16.dp)
                )
                BasicTextField(
                    value = newSurname,
                    onValueChange = { newValue: String -> newSurname = newValue },
                    modifier = Modifier
                        .fillMaxWidth()
                        .onFocusChanged { focusState ->
                            if (!focusState.isFocused) {
                                saveProfile()
                            }
                        },
                    textStyle = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.onBackground
                    ),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = { focusRequester.requestFocus() },
                        onDone = {
                            focusManager.clearFocus()
                            saveProfile()
                        }
                    ),
                    singleLine = true
                )

                Text(
                    text = "Sex",
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier
                        .padding(top = 16.dp)
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    RadioButton(
                        selected = newSex == "M",
                        onClick = { newSex = "M" },
                        colors = RadioButtonDefaults.colors(
                                unselectedColor = MaterialTheme.colorScheme.onBackground,
                                selectedColor = MaterialTheme.colorScheme.onBackground
                        )
                    )
                    Text(
                        text = "M",
                        color = MaterialTheme.colorScheme.onBackground
                        )
                    RadioButton(
                        selected = newSex == "F",
                        onClick = { newSex = "F" },
                        colors = RadioButtonDefaults.colors(
                            unselectedColor = MaterialTheme.colorScheme.onBackground,
                            selectedColor = MaterialTheme.colorScheme.onBackground
                        )
                    )
                    Text(
                        text = "F",
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }

                Text(
                    text = "Birthday",
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier
                        .padding(top = 16.dp)
                )
                DatePicker(
                    state = datePicker,
                    modifier = Modifier
                        .fillMaxWidth(),
                    showModeToggle = false,
                    colors = DatePickerDefaults
                        .colors(titleContentColor = MaterialTheme.colorScheme.onBackground,
                            navigationContentColor = MaterialTheme.colorScheme.onBackground,
                            headlineContentColor = MaterialTheme.colorScheme.onBackground
                        )
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
        ) {
            MainBottomBar(navController)
        }
    }
}