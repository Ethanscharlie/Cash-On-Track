package com.example.financemanager

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

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

        composable(route = Screen.Tracker.name) {
            TrackerAdd(navController)
        }
    }
}

