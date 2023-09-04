package com.example.moodtrackr.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.moodtrackr.models.MoodEntry
import java.time.LocalDate

@Composable
fun MoodEntrySummary(
    moodEntry: MoodEntry?
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = LocalDate.now().toString(),
            style = MaterialTheme.typography.bodyMedium.copy(fontSize = 18.sp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        if (moodEntry == null) {
            ////    //TODO onClick = {  onEditClick() },
            //    onClick = onClick(),
             //   modifier = Modifier.align(Alignment.CenterHorizontally)
            //) {
            //    Text("Add Mood Entry")
            //}
        } else {
            MoodIndicator(
                label = "Happiness",
                progress = moodEntry.Happiness.toFloat() / 100,
                value = moodEntry.Happiness.toString()
            )

            MoodIndicator(
                label = "Anger",
                progress = moodEntry.Anger.toFloat() / 100,
                value = moodEntry.Anger.toString()
            )

            MoodIndicator(
                label = "Love",
                progress = moodEntry.Love.toFloat() / 100,
                value = moodEntry.Love.toString()
            )

            MoodIndicator(
                label = "Stress",
                progress = moodEntry.Stress.toFloat() / 100,
                value = moodEntry.Stress.toString()
            )

            MoodIndicator(
                label = "Energy",
                progress = moodEntry.Energy.toFloat() / 100,
                value = moodEntry.Energy.toString()
            )

            MoodIndicator(
                label = "Sleep",
                progress = moodEntry.Sleep.toFloat() / 100,
                value = moodEntry.Sleep.toString()
            )

            MoodIndicator(
                label = "Health",
                progress = moodEntry.Health.toFloat() / 100,
                value = moodEntry.Health.toString()
            )

            MoodIndicator(
                label = "Depression",
                progress = moodEntry.Depression.toFloat() / 100,
                value = moodEntry.Depression.toString()
            )

            MoodTextIndicator(
                label = "Notes",
                text = moodEntry.Notes
            )
        }
    }
}

fun onClick()
{

}