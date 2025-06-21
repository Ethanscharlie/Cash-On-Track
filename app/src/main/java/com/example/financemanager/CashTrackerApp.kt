package com.example.financemanager

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CashTrackerApp(
    navController: NavHostController = rememberNavController()
) {
    Navigation(navController)
}

@Composable
fun Navigation(
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Balace.name,
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

        composable(route = Screen.ManageRecords.name) {
            ManageRecords(navController)
        }
    }
}