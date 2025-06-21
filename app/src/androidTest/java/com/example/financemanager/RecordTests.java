package com.example.financemanager;

import androidx.test.core.app.ApplicationProvider;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.time.LocalDate;

import model.Database;
import model.Record;
import model.Tracker;

public class RecordTests {
    private void createTestingDatabase() throws JSONException, IOException {
        Database.init(ApplicationProvider.getApplicationContext());
        Database database = Database.getInstance();
        database.createNewDatabaseOnFilesystem();
    }

    @Test
    public void canAddRecordsAndGetBalance() throws Exception {
        createTestingDatabase();

        Record.addRecord(100, "");
        Record.addRecord(34.5, "");
        final double total = Record.getTotalSpentForTracker("");
        Assert.assertEquals(134.5, total, 0.01);
    }


    @Test
    public void canRemoveRecords() throws Exception {
        createTestingDatabase();
        final Database database = Database.getInstance();

        Record.addRecord(10, "One");
        Record.addRecord(11, "One");
        Record.addRecord(12, "One");

        JSONObject json = database.getAllItemsFromTable("records").get(1);
        Record.remove(json.getString("id"));

        Assert.assertEquals(2, database.getAllItemsFromTable("records").size());
        Assert.assertEquals(12, database.getAllItemsFromTable("records").get(1).get("cash"));
    }
}
