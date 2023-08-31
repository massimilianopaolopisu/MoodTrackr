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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.moodtrackr.models.Profile
import com.example.moodtrackr.repositories.ProfilePreferenceManager
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(navController: NavController) {
    val context = LocalContext.current
    val profilePreferenceManager = ProfilePreferenceManager(context)
    val profile = profilePreferenceManager.load()

    var newName by remember { mutableStateOf(profile.name) }
    var newSurname by remember { mutableStateOf(profile.surname) }
    var newSex by remember { mutableStateOf(profile.sex) }
    var newBirthday by remember { mutableStateOf(profile.birthday) }

    val selectedDate = LocalDate.parse(newBirthday, DateTimeFormatter.ofPattern("dd/MM/yyyy"))
    val datePicker = rememberDatePickerState(selectedDate.toEpochDay() * (24 * 60 * 60 * 1000))

    val focusRequester = FocusRequester()
    val focusManager = LocalFocusManager.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
        ) {
            Text(
                text = "Please enter your details:",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    textAlign = TextAlign.Center
                ),
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }

        LazyColumn (
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .align(Alignment.TopCenter)
                .padding(top = 50.dp)
        ) {
            item {
                Text(
                    text = "Name",
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center
                    ),
                    modifier = Modifier
                        .padding(top = 16.dp)
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
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center
                    ),
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
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center
                    ),
                    modifier = Modifier
                        .padding(top = 16.dp)
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    RadioButton(
                        selected = newSex == "M",
                        onClick = { newSex = "M" }
                    )
                    Text("M", modifier = Modifier.alignByBaseline())
                    RadioButton(
                        selected = newSex == "F",
                        onClick = { newSex = "F" }
                    )
                    Text("F", modifier = Modifier.alignByBaseline())
                }

                Text(
                    text = "Birthday",
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center
                    ),
                    modifier = Modifier
                        .padding(top = 16.dp)
                )
                DatePicker(
                    state = datePicker,
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
        }

            //Spacer(modifier = Modifier.height(16.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
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
                        profilePreferenceManager.save(Profile(newName, newSurname, newSex, newBirthday))
                        navController.navigate("home")
                    },
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                ) {
                    Text("Save")
                }
            }
        }
    }
}