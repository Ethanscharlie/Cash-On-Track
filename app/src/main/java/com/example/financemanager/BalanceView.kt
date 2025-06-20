package com.example.financemanager

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import model.Tracker

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

