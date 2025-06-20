package com.example.financemanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.financemanager.ui.theme.FinanceManagerTheme
import model.Tracker
import model.Record
import model.Database

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Database.init(this);

        val database = Database.getInstance()
        database.createNewDatabaseOnFilesystem()

        Tracker.addTracker("MainTracker", Tracker.MONTHLY, 100.0);

        enableEdgeToEdge()
        setContent {
            FinanceManagerTheme {
                CashTrackerApp()
            }
        }
    }
}