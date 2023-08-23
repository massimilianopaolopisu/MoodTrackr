package com.example.moodtrackr

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import com.example.moodtrackr.ui.theme.MoodTrackrTheme
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MoodTrackrApp()
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun MoodTrackrApp() {
    val context = LocalContext.current

    val sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
    val savedName = sharedPreferences.getString("name", "")
    val savedSurname = sharedPreferences.getString("surname", "")
    val savedSex = sharedPreferences.getString("sex", "")
    val savedBirthday = sharedPreferences.getString("birthday", "")

    // Wrap the content of the app with the theme
    MoodTrackrTheme {
        Content(savedName, savedSurname, savedSex, savedBirthday, sharedPreferences)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Content(
    savedName: String?,
    savedSurname: String?,
    savedSex: String?,
    savedBirthday: String?,
    sharedPreferences: SharedPreferences
) {
    var nameState by remember { mutableStateOf(savedName ?: "Name") }
    var surnameState by remember { mutableStateOf(savedSurname ?: "Surname") }
    var sexState by remember { mutableStateOf(savedSex ?: "M") }
    var birthdayState by remember { mutableStateOf(savedBirthday ?: "") }

    if (birthdayState == null || birthdayState.isBlank())
        birthdayState = "01/01/1990"

    val selectedDate = LocalDate.parse(birthdayState, DateTimeFormatter.ofPattern("dd/MM/yyyy"))
    val datePicker = rememberDatePickerState(selectedDate.toEpochDay() * (24 * 60 * 60 * 1000))

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Description Text
        Text(
            text = "Please enter your details:",
            style = MaterialTheme.typography.titleSmall.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                textAlign = TextAlign.Center
            ),
            modifier = Modifier.padding(vertical = 8.dp)
        )

        Text("Name")
        BasicTextField(
            value = nameState,
            onValueChange = { newValue: String -> nameState = newValue },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .padding(8.dp),
            textStyle = MaterialTheme.typography.bodyMedium,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    // Handle keyboard Done action if needed
                }
            ),
            singleLine = true
        )

        Text("Surname")
        BasicTextField(
            value = surnameState,
            onValueChange = { newValue: String -> surnameState = newValue },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .padding(8.dp),
            textStyle = MaterialTheme.typography.bodyMedium,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    // Handle keyboard Done action if needed
                }
            ),
            singleLine = true
        )

        Text("Sex")
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
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

        Text("Birthday")
        DatePicker(
            state = datePicker,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .padding(8.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                // Save the text field values to SharedPreferences
                val editor = sharedPreferences.edit()
                editor.putString("name", nameState)
                editor.putString("surname", surnameState)
                editor.putString("sex", sexState)
                editor.putString("birthday", birthdayState)
                editor.apply()
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            Text("Save")
        }
    }
}
