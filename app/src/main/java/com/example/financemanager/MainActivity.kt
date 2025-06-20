package com.example.financemanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.StringRes
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
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.financemanager.ui.theme.FinanceManagerTheme
import model.Tracker
import model.Record
import model.Database

enum class Screen(@StringRes val title: Int) {
    Balace(title = 0),
    Record(title = 1)
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Database.init(this);

        val database = Database.getInstance()
        database.createNewDatabaseOnFilesystem()

        Tracker.addTracker("MainTracker", Tracker.MONTHLY, 100.0);

        enableEdgeToEdge()
        setContent {
            FinanceManagerTheme {
                CashTrackerApp()
            }
        }
    }
}

@Composable
fun CashTrackerApp(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Balace.name,
        modifier = Modifier.padding(40.dp)
    ) {
        composable(route = Screen.Balace.name) {
            BalanceView(navController)
        }

        composable(route = Screen.Record.name) {
            RecordEntry(navController)
        }
    }
}

@Composable
fun BalanceView(
    navController: NavHostController
) {
    Column (Modifier.padding(40.dp)) {
        Text(Tracker.getBalanceOfTracker("MainTracker").toString())

        Button(onClick = {
            navController.navigate(Screen.Record.name)
        }) {
            Text(text = "Add Record")
        }
    }
}

@Composable
fun RecordEntry(
    navController: NavHostController
) {
    Column (Modifier.padding(40.dp)) {
        var text by remember { mutableStateOf("") }

        OutlinedTextField (
            value = text,
            onValueChange = { text = it },
            label = { Text("Label") },
            maxLines = 1,
        )

        Button(onClick = {
            Record.addRecord(text.toDouble(), "MainTracker");
            navController.popBackStack()
//            trackedValue = Tracker.getBalanceOfTracker("MainTracker").toString();
        }) {
            Text(text = "Click Me")
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