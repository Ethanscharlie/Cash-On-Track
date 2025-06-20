package com.example.financemanager;

import androidx.test.core.app.ApplicationProvider;

import org.json.JSONException;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.time.LocalDate;

import model.Database;
import model.Record;
import model.Tracker;

public class TrackerPlusRecordTest {
    private void createTestingDatabase() throws JSONException, IOException {
        Database.init(ApplicationProvider.getApplicationContext());
        Database database = Database.getInstance();
        database.createNewDatabaseOnFilesystem();
    }

    @Test
    public void monthlyBalanceIsCorrectForFuture() throws Exception {
        createTestingDatabase();

        Tracker.addTracker("One", Tracker.MONTHLY, 100);
        Record.addRecord(20, "One");

        final LocalDate future = LocalDate.now().plusMonths(20);

        final double balance = Tracker.getBalanceOfTrackerForDate("One", future);
        Assert.assertEquals(2080, balance, 0.01);
    }


    @Test
    public void weeklyBalanceIsCorrectForFuture() throws Exception {
        createTestingDatabase();

        Tracker.addTracker("One", Tracker.WEEKLY, 25);
        Record.addRecord(20, "One");

        final LocalDate future = LocalDate.now().plusWeeks(100);

        final double balance = Tracker.getBalanceOfTrackerForDate("One", future);
        Assert.assertEquals(25 - 20 + (25 * 100), balance, 0.01);
    }

    @Test
    public void canAddMultipleTrackersAndRemoveFromOne() throws Exception {
        createTestingDatabase();

        Tracker.addTracker("One", Tracker.WEEKLY, 100);
        Tracker.addTracker("Two", Tracker.WEEKLY, 25);

        Record.addRecord(10, "Two");

        final double balance = Tracker.getBalanceOfTracker("Two");
        Assert.assertEquals(15, balance, 0.01);

        final double balanceOne = Tracker.getBalanceOfTracker("One");
        Assert.assertEquals(100, balanceOne, 0.01);
    }
}
