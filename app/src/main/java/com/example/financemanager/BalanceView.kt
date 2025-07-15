package com.example.financemanager

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import model.Tracker
import java.util.Locale

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
                },

                navigationIcon = {
                    val expanded = remember { mutableStateOf(false) }

                    TextButton (onClick = {
                        expanded.value = true
                    }) {
                        Icon(
                            imageVector = Icons.Filled.Menu,
                            contentDescription = "Localized description"
                        )
                    }

                    DropMenu(expanded, navController)
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
        }

    }
}

@Composable
fun DropMenu(
    expanded: MutableState<Boolean>,
    navController: NavHostController
) {
    Box(
        modifier = Modifier
            .padding(16.dp)
    ) {
        DropdownMenu (
            expanded = expanded.value,
            onDismissRequest = { expanded.value = false }
        ) {
            DropdownMenuItem(
                text = { Text("Manage Records") },
                onClick = {
                    navController.navigate(Screen.ManageRecords.name)
                    expanded.value = false
                }
            )
            DropdownMenuItem(
                text = { Text("Add Tracker") },
                onClick = {
                    navController.navigate(Screen.Tracker.name)
                    expanded.value = false
                }
            )
        }
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
    }
}

@Composable
fun TrackerBalance(trackerName: String) {
    if (Tracker.findTracker(trackerName) == null) {
        return;
    }

    val showBottomSheet = remember { mutableStateOf(false) }

    if (showBottomSheet.value) {
        RemoveBalanceSheet(trackerName, showBottomSheet)
    }

    Card {
        TextButton(
            onClick = {
                showBottomSheet.value = true;
            }
        ) {
            Row(Modifier.padding(16.dp)) {
                Text(trackerName)
                Spacer(Modifier.weight(1f))
                Text(String.format(Locale.ENGLISH, "$ %.2f", Tracker.getBalanceOfTracker(trackerName)))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RemoveBalanceSheet(
    tracker: String,
    showBottomSheet: MutableState<Boolean>
) {
    ModalBottomSheet(
        onDismissRequest = {
            showBottomSheet.value = false
        },
    ) {
        Column (
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text("Remove $tracker?")

            Row {
                Button(
                    onClick = {
                        Tracker.remove(tracker)
                        showBottomSheet.value = false
                    }
                ) {
                    Text("Confirm")
                }

                OutlinedButton (
                    onClick = {
                        showBottomSheet.value = false
                    }
                ) {
                    Text("Cancel")
                }
            }
        }
    }
}