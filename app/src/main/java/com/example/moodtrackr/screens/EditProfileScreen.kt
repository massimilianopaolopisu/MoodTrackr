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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.moodtrackr.components.SaveBottomBar
import com.example.moodtrackr.models.Profile
import com.example.moodtrackr.repositories.interfaces.IProfilePreferencesRepository
import com.example.moodtrackr.repositories.interfaces.ISave
import com.example.moodtrackr.utilities.DateUtilities

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(
    navController: NavController,
    profilePreferencesRepository: IProfilePreferencesRepository
) {
    val profile = profilePreferencesRepository.load()

    var newName by remember { mutableStateOf(profile.name) }
    var newSurname by remember { mutableStateOf(profile.surname) }
    var newSex by remember { mutableStateOf(profile.sex) }
    var newBirthday by remember { mutableStateOf(profile.birthday) }

    val datePicker = rememberDatePickerState(DateUtilities.getMillisFromLocalDate(newBirthday))

    val focusRequester = FocusRequester()
    val focusManager = LocalFocusManager.current

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
            Text(
                text = "Edit Profile",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color.Black),
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .align(Alignment.CenterHorizontally)
                    .weight(1f)
            )
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
                    modifier = Modifier
                        .padding(top = 8.dp)
                )
                BasicTextField(
                    value = newName,
                    onValueChange = { newValue: String -> newName = newValue },
                    modifier = Modifier
                        .fillMaxWidth(),
                    textStyle = MaterialTheme.typography.bodyMedium,
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = { focusRequester.requestFocus() },
                        onDone = { focusManager.clearFocus() }
                    ),
                    singleLine = true
                )

                Text(
                    text = "Surname",
                    modifier = Modifier
                        .padding(top = 16.dp)
                )
                BasicTextField(
                    value = newSurname,
                    onValueChange = { newValue: String -> newSurname = newValue },
                    modifier = Modifier
                        .fillMaxWidth(),
                    textStyle = MaterialTheme.typography.bodyMedium,
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = { focusRequester.requestFocus() },
                        onDone = { focusManager.clearFocus() }
                    ),
                    singleLine = true
                )

                Text(
                    text = "Sex",
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
                        onClick = { newSex = "M" }
                    )
                    Text("M")
                    RadioButton(
                        selected = newSex == "F",
                        onClick = { newSex = "F" }
                    )
                    Text("F")
                }

                Text(
                    text = "Birthday",
                    modifier = Modifier
                        .padding(top = 16.dp)
                )
                DatePicker(
                    state = datePicker,
                    modifier = Modifier
                        .fillMaxWidth(),
                    showModeToggle = false,
                    colors = DatePickerDefaults
                        .colors(titleContentColor = Color.Black,
                            navigationContentColor = Color.Black,
                            headlineContentColor = Color.Black)
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
        ) {
            newBirthday = DateUtilities.getLocalDateFromMillis(datePicker.selectedDateMillis?: 0)
            @Suppress("UNCHECKED_CAST")
            val saveHandlerAndObjectPairList: List<Pair<ISave<Any>, Any>> = listOf(
                profilePreferencesRepository as ISave<Any> to Profile(newName, newSurname, newSex, newBirthday) as Any
            )

            SaveBottomBar(navController, saveHandlerAndObjectPairList)
        }
    }
}