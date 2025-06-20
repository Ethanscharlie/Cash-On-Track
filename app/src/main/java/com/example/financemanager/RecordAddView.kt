package com.example.financemanager

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import model.Record

@Composable
fun RecordEntry(
    navController: NavHostController
) {
    Column (Modifier.padding(40.dp)) {
        var text by remember { mutableStateOf("") }

        OutlinedTextField (
            value = text,
            onValueChange = { text = it },
            label = { Text("Label") },
            maxLines = 1,
        )

        Button(onClick = {
            Record.addRecord(text.toDouble(), "MainTracker");
            navController.popBackStack()
        }) {
            Text(text = "Click Me")
        }

    }
}

