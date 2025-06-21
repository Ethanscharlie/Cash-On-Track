package com.example.financemanager

import android.graphics.drawable.Icon
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import model.Tracker

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BalanceView(
    navController: NavHostController
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),

                title = {
                    Text("Balances")
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton (onClick = {
                navController.navigate(Screen.Record.name)
            }) {
                Icon(Icons.Rounded.Add, contentDescription = "Add")
            }
        }
    ) { innerPadding ->
        Column (
            Modifier.padding(40.dp).padding(innerPadding)
        ) {
            TrackerBalanceList(navController)
            Spacer(Modifier.weight(1f))
            Buttons(navController)
        }

    }
}

@Composable
fun Buttons(
    navController: NavHostController
) {
    Card {
        Column (Modifier.padding(8.dp)) {
            AddNavButton("Manage Records", Screen.ManageRecords, navController)
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
fun TrackerBalanceList(navController: NavHostController) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        val trackerNamesList = Tracker.getAvailableTrackers()
        for (trackerName in trackerNamesList) {
            TrackerBalance(trackerName)
        }

        OutlinedButton (
            onClick = {
                navController.navigate(Screen.Tracker.name)
            },
            modifier = Modifier
                .width(1000.dp)
        ) {
            Icon(Icons.Rounded.Add, contentDescription = "Add")
        }
    }
}

@Composable
fun TrackerBalance(trackerName: String) {
    Card {
        Row (Modifier.padding(16.dp)) {
            Text(trackerName)
            Spacer(Modifier.weight(1f))
            Text("$ " + Tracker.getBalanceOfTracker(trackerName).toString())
        }
    }
}