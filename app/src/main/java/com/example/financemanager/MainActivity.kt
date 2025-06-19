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
import controller.BudgetTrackerController


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        MainController.getInstance().database.createNewDatabaseOnFilesystem()

        enableEdgeToEdge()
        setContent {
            FinanceManagerTheme {
                Column (Modifier.padding(40.dp)) {
                    var text by remember { mutableStateOf("") }

                    OutlinedTextField (
                        value = text,
                        onValueChange = { text = it },
                        label = { Text("Label") },
                        maxLines = 1,
                    )

                    Button(onClick = {
                        BudgetTrackerController.getInstance().record(10.0);
                    }) {
                        Text(text = "Click Me")
                    }
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