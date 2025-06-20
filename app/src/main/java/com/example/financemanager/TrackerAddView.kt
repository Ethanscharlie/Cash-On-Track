package com.example.financemanager

import androidx.compose.foundation.layout.padding
import androidx.compose.ui.unit.dp
import androidx.test.services.storage.file.PropertyFile.Column
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
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

@Composable
fun TrackerAdd(
    navController: NavHostController
) {
    Column (Modifier.padding(40.dp)) {
        val nameMutable = remember { mutableStateOf("") }
        val cashMutable = remember { mutableStateOf("") }
        val periodMutable = remember { mutableStateOf("monthly") }

        NameField(nameMutable)
        CashField(cashMutable)
        PeriodTypeField(periodMutable)

        Button(onClick = {
            Tracker.addTracker(nameMutable.value, periodMutable.value, cashMutable.value.toDouble())
            navController.popBackStack()
        }) {
            Text(text = "Add")
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
    )
}

@Composable
fun PeriodTypeField(periodMutable: MutableState<String>, modifier: Modifier = Modifier) {
    val radioOptions = listOf("monthly", "weekly")

    Column(modifier.selectableGroup()) {
        radioOptions.forEach { text ->
            Row (
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

@Composable
fun CashField(cashMutable: MutableState<String>) {
    OutlinedTextField (
        value = cashMutable.value,
        onValueChange = { cashMutable.value = it },
        label = { Text("Cash Field") },
        maxLines = 1,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
    )
}
