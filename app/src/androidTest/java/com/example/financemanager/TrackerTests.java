package com.example.financemanager;

import androidx.test.core.app.ApplicationProvider;

import org.json.JSONException;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;

import model.Database;
import model.Tracker;

public class TrackerTests {
    private void createTestingDatabase() throws JSONException, IOException {
        Database.init(ApplicationProvider.getApplicationContext());
        Database database = Database.getInstance();
        database.createNewDatabaseOnFilesystem();
    }

    @Test
    public void canAddTrackersAndTheyShowAsAvailable() throws Exception {
        createTestingDatabase();

        Tracker.addTracker("One", Tracker.PeriodType.Monthly, 100);
        Tracker.addTracker("Two", Tracker.PeriodType.Weekly, 20);

        final ArrayList<String> trackerNames = Tracker.getAvailableTrackers();
        Assert.assertEquals("One", trackerNames.get(0));
        Assert.assertEquals("Two", trackerNames.get(1));
    }

    @Test
    public void monthlyBalanceIsCorrect() throws Exception {
        createTestingDatabase();
        Tracker.addTracker("One", Tracker.PeriodType.Monthly, 130.23);
        final double balance = Tracker.getBalance("One");
        Assert.assertEquals(130.23, balance, 0.01);
    }

    @Test
    public void weeklyBalanceIsCorrect() throws Exception {
        createTestingDatabase();
        Tracker.addTracker("One", Tracker.PeriodType.Weekly, 130.23);
        final double balance = Tracker.getBalance("One");
        Assert.assertEquals(130.23, balance, 0.01);
    }
}
