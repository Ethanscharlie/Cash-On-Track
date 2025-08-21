package com.example.financemanager

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import model.Database
import model.Record
import org.json.JSONObject
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManageRecords(
    navController: NavController
) {

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),

                title = {
                    Text("Manage Records")
                },

                navigationIcon = {
                    TextButton (onClick = {
                        navController.navigate(Screen.Balace.name)
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Localized description"
                        )
                    }
                }
            )
        },
    ) { innerPadding ->
        Column (
            Modifier
                .padding(30.dp)
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {
            RecordList(navController)
        }

    }
}
@Composable
fun RecordList(navController: NavController) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        val database = Database.getInstance();
        val records = database.getAllItemsFromTable("records")
        for (i in records.indices.reversed()) {
            RecordItem(records[i], navController)
        }
    }
}

@Composable
fun RecordItem(recordJSON: JSONObject, navController: NavController) {
    Card (
        Modifier
            .padding(bottom = 4.dp)
    ) {
        Row (Modifier.padding(10.dp)) {
            Column {
                Text(
                    "$ " +
                            recordJSON.getString("cash") +
                            "   •   " +
                            recordJSON.getString("note")
                )

                Row {
                    val subtext = recordJSON.getString("date") +
                                  " • " +
                                  recordJSON.getString("tracker")
                    Text(
                        text = subtext,
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray
                    )
                }
            }
            Spacer(Modifier.weight(1f))
            RemoveButton(recordJSON.getString("id"), navController)
        }
    }
}

@Composable
fun RemoveButton(recordID: String, navController: NavController) {
    Button(onClick = {
        Record.remove(recordID)
        navController.navigate(Screen.Balace.name)
    }) {
        Icon(Icons.Rounded.Delete, contentDescription = "Delete")
    }
}