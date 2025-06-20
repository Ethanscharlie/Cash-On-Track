package com.example.financemanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.financemanager.ui.theme.FinanceManagerTheme
import model.Tracker
import model.Record
import model.Database


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Database.init(this);

        val database = Database.getInstance()
        database.createNewDatabaseOnFilesystem()

        Tracker.addTracker("MainTracker", Tracker.PeriodType.Monthly, 100.0);

        enableEdgeToEdge()
        setContent {
            FinanceManagerTheme {
                Column (Modifier.padding(40.dp)) {
                    var trackedValue by remember { mutableStateOf("") }
                    trackedValue = Tracker.getBalanceOfTracker("MainTracker").toString();

                    var text by remember { mutableStateOf("") }

                    OutlinedTextField (
                        value = text,
                        onValueChange = { text = it },
                        label = { Text("Label") },
                        maxLines = 1,
                    )

                    Button(onClick = {
                        Record.addRecord(text.toDouble(), "MainTracker");
                        trackedValue = Tracker.getBalanceOfTracker("MainTracker").toString();
                    }) {
                        Text(text = "Click Me")
                    }

                    Text(trackedValue)
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    FinanceManagerTheme {
        Greeting("Android")
    }
}