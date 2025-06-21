package com.example.financemanager

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
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
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.Icon

@Composable
fun ManageRecords(
    navController: NavController
) {
    Column (Modifier.padding(30.dp)) {
        RecordList(navController)

        Button(onClick = {
            navController.popBackStack()
        }) {
            Text(text = "Cancel")
        }
    }
}
@Composable
fun RecordList(navController: NavController) {
    Column {
        val database = Database.getInstance();
        val records = database.getAllItemsFromTable("records")
        for (record in records) {
            RecordItem(record, navController)
        }
    }
}

@Composable
fun RecordItem(recordJSON: JSONObject, navController: NavController) {
    Card {
        Row (Modifier.padding(10.dp)) {
            Column {
                Text("$ " + recordJSON.getString("cash"))
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