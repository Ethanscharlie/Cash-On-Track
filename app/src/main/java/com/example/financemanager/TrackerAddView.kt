package com.example.financemanager

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import model.Record
import model.Tracker

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrackerAdd(
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
                    Text("Add Tracker")
                },

                navigationIcon = {
                    TextButton (onClick = { navController.navigate(Screen.Balace.name) }) {
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
            Modifier.padding(40.dp).padding(innerPadding)
        ) {
            MainAddFieldEntry(navController)
        }

    }
}

@Composable
fun MainAddFieldEntry(
    navController: NavHostController
) {
    Column {
        val nameMutable = remember { mutableStateOf("") }
        val cashMutable = remember { mutableStateOf("") }
        val periodMutable = remember { mutableStateOf("monthly") }

        NameField(nameMutable)
        CashField(cashMutable)
        PeriodTypeField(periodMutable)

        Button(
            onClick = {
                if (!Tracker.isValidDouble(cashMutable.value)) {
                    return@Button
                }

                if (nameMutable.value == "") {
                    return@Button
                }

                Tracker.addTracker(nameMutable.value, periodMutable.value, cashMutable.value.toDouble())
                navController.popBackStack()
            },
            modifier = Modifier
                .padding(vertical = 16.dp)
                .width(1000.dp)
        ) {
            Text(text = "Enter")
        }
    }
}

@Composable
fun NameField(nameMutable: MutableState<String>) {
    OutlinedTextField (
        value = nameMutable.value,
        onValueChange = { nameMutable.value = it },
        label = { Text("Name Field") },
        maxLines = 1,
        modifier = Modifier
            .padding(bottom = 10.dp)
            .width(1000.dp)
    )
}

@Composable
fun PeriodTypeField(periodMutable: MutableState<String>, modifier: Modifier = Modifier) {
    val radioOptions = listOf("monthly", "weekly")


    Card (
        modifier = Modifier
            .padding(vertical = 10.dp)
    ) {
        Column(modifier.selectableGroup()) {
            radioOptions.forEach { text ->
                Row(
                    Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .selectable(
                            selected = (text == periodMutable.value),
                            onClick = { periodMutable.value = text },
                            role = Role.RadioButton
                        )
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = (text == periodMutable.value),
                        onClick = null // null recommended for accessibility with screen readers
                    )
                    Text(
                        text = text,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun CashField(cashMutable: MutableState<String>) {
    OutlinedTextField (
        value = cashMutable.value,
        onValueChange = { cashMutable.value = it },
        label = { Text("Cash Field") },
        maxLines = 1,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        modifier = Modifier
            .padding(bottom = 10.dp)
            .width(1000.dp)
    )
}
