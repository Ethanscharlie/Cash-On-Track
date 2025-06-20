package com.example.financemanager;

import androidx.test.core.app.ApplicationProvider;

import org.json.JSONException;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.time.LocalDate;

import model.Database;
import model.Record;

public class RecordTests {
    private void createTestingDatabase() throws JSONException, IOException {
        Database.init(ApplicationProvider.getApplicationContext());
        Database database = Database.getInstance();
        database.createNewDatabaseOnFilesystem();
    }

    @Test
    public void canAddRecordsAndGetBalance() throws JSONException, IOException {
        createTestingDatabase();

        Record.addRecord(100, "");
        Record.addRecord(34.5, "");
        final double total = Record.getTotalSpentForTracker("");
        Assert.assertEquals(134.5, total, 0.01);
    }
}
