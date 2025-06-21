package com.example.financemanager

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import model.Tracker

@Composable
fun BalanceView(
    navController: NavHostController
) {
    Column (Modifier.padding(40.dp)) {
        TrackerBalanceList()
        Spacer(Modifier.weight(1f))
        Buttons(navController)
    }
}

@Composable
fun Buttons(
    navController: NavHostController
) {
    AddNavButton("Manage Records", Screen.ManageRecords, navController)

    AddNavButton("Add Record", Screen.Record, navController)
    AddNavButton("Add Tracker", Screen.Tracker, navController)
}

@Composable
fun AddNavButton(text: String,  location: Screen, navController: NavController) {
    Button(onClick = {
        navController.navigate(location.name)
    }) {
        Text(text = text)
    }
}

@Composable
fun TrackerBalanceList() {
    Column {
        val trackerNamesList = Tracker.getAvailableTrackers()
        for (trackerName in trackerNamesList) {
            TrackerBalance(trackerName)
        }
    }
}

@Composable
fun TrackerBalance(trackerName: String) {
    Row {
        Text(trackerName)
        Spacer(Modifier.weight(1f))
        Text("$ " + Tracker.getBalanceOfTracker(trackerName).toString())
    }
}