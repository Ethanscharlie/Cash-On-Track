package com.example.financemanager

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Button
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
fun RecordEntry(
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
                    Text("Add Record")
                },

                navigationIcon = {
                    TextButton (onClick = { navController.popBackStack() }) {
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
            MainFieldEntry(navController)
        }

    }
}

@Composable
fun MainFieldEntry(navController: NavHostController) {
    Column (Modifier.padding(40.dp)) {
        var text by remember { mutableStateOf("") }
        val tracker = remember { mutableStateOf("") }

        TrackerField(tracker)

        OutlinedTextField (
            value = text,
            onValueChange = { text = it },
            label = { Text("Cash") },
            maxLines = 1,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Button(onClick = {
            Record.addRecord(text.toDouble(), tracker.value);
            navController.popBackStack()
        }) {
            Text(text = "Enter")
        }
    }
}

@Composable
fun TrackerField(trackerMutable: MutableState<String>, modifier: Modifier = Modifier) {
    val radioOptions = Tracker.getAvailableTrackers()

    Column(modifier.selectableGroup()) {
        radioOptions.forEach { text ->
            Row (
                Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .selectable(
                        selected = (text == trackerMutable.value),
                        onClick = { trackerMutable.value = text },
                        role = Role.RadioButton
                    )
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = (text == trackerMutable.value),
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
