package com.example.moodtrackr

import android.content.Context
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.Alignment
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(navController: NavController) {
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
    val savedName = sharedPreferences.getString("name", "")
    val savedSurname = sharedPreferences.getString("surname", "")
    val savedSex = sharedPreferences.getString("sex", "")
    val savedBirthday = sharedPreferences.getString("birthday", "")

    var nameState by remember { mutableStateOf(savedName ?: "") }
    var surnameState by remember { mutableStateOf(savedSurname ?: "") }
    var sexState by remember { mutableStateOf(savedSex ?: "") }
    var birthdayState by remember { mutableStateOf(savedBirthday ?: "") }

    if (nameState.isBlank())
        nameState = "user"

    if (sexState.isBlank())
        sexState = "M"

    if (birthdayState.isBlank())
        birthdayState = "01/01/1990"

    val selectedDate = LocalDate.parse(birthdayState, DateTimeFormatter.ofPattern("dd/MM/yyyy"))
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

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .align(Alignment.TopCenter)
                .padding(top = 50.dp)
        ) {
            LazyColumn (
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
                        value = nameState,
                        onValueChange = { newValue: String -> nameState = newValue },
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
                        value = surnameState,
                        onValueChange = { newValue: String -> surnameState = newValue },
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
                            selected = sexState == "M",
                            onClick = { sexState = "M" }
                        )
                        Text("M", modifier = Modifier.alignByBaseline())
                        RadioButton(
                            selected = sexState == "F",
                            onClick = { sexState = "F" }
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
                        val editor = sharedPreferences.edit()
                        editor.putString("name", nameState)
                        editor.putString("surname", surnameState)
                        editor.putString("sex", sexState)
                        editor.putString("birthday", birthdayState)
                        editor.apply()
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