package com.example.financemanager

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
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
    Card {
        Column (Modifier.padding(8.dp)) {
            AddNavButton("Manage Records", Screen.ManageRecords, navController)
            AddNavButton("Add Record", Screen.Record, navController)
            AddNavButton("Add Tracker", Screen.Tracker, navController)
        }
    }
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
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        val trackerNamesList = Tracker.getAvailableTrackers()
        for (trackerName in trackerNamesList) {
            TrackerBalance(trackerName)
        }
    }
}

@Composable
fun TrackerBalance(trackerName: String) {
    Card {
        Row (Modifier.padding(10.dp)) {
            Text(trackerName)
            Spacer(Modifier.weight(1f))
            Text("$ " + Tracker.getBalanceOfTracker(trackerName).toString())
        }
    }
}