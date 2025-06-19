package com.example.financemanager;

import android.content.Context;
import android.provider.ContactsContract;

import model.BudgetTracker;
import model.Constants;
import controller.BudgetTrackerController;
import model.Database;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.json.JSONException;
import org.junit.Test;
import org.junit.Assert;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.time.LocalDate;

@RunWith(AndroidJUnit4.class)
public class CashTrackerTests {
    static final Context context = ApplicationProvider.getApplicationContext();

    private Database setupTestingDatabase() throws JSONException, IOException {
        final BudgetTracker tracker = new BudgetTracker(100, LocalDate.now(), 0);
        final Database database = Database.getInstance();
        database.createNewDatabaseOnFilesystem();
        database.addItemToTable(tracker.toJSON(), "tracker");
        return database;
    }

    @Test
    public void comparisonIsCloseEnough() {
        final double x = 100.0;
        final double y = 100.0;
        Assert.assertEquals(x, y, Constants.COMPARISON_DELTA);
    }

    @Test
    public void comparisonIsToFar() {
        final double x = 99.99;
        final double y = 100.0;
        Assert.assertNotEquals(x, y, Constants.COMPARISON_DELTA);
    }

    @Test
    public void testGetMonthsElapsedOverManyMonths() throws JSONException, IOException {
        setupTestingDatabase();
        final LocalDate past = LocalDate.now().minusMonths(20);

        final BudgetTracker tracker = new BudgetTracker(100, past, 0);
        final Database database = Database.getInstance();
        database.createNewDatabaseOnFilesystem();
        database.addItemToTable(tracker.toJSON(), "tracker");

        final BudgetTrackerController budgetTrackerController = BudgetTrackerController.getInstance();
        final int monthsElapsed = budgetTrackerController.getMonthsElapsed();
        Assert.assertEquals(20, monthsElapsed);
    }

    @Test
    public void testGetMonthsElapsedOverNoMonths() throws JSONException, IOException {
        setupTestingDatabase();
        final BudgetTrackerController budgetTrackerController = BudgetTrackerController.getInstance();
        final int monthsElapsed = budgetTrackerController.getMonthsElapsed();
        Assert.assertEquals(0, monthsElapsed);
    }

    @Test
    public void trackerPoolIsCorrectOnInit() throws JSONException, IOException {
        setupTestingDatabase();
        final BudgetTrackerController budgetTrackerController = BudgetTrackerController.getInstance();
        final double availableFunds = budgetTrackerController.getAvailableFunds();
        Assert.assertEquals(100.0, availableFunds, Constants.COMPARISON_DELTA);
    }

    @Test
    public void canCollectAllRecords() throws JSONException, IOException {
        setupTestingDatabase();
        final BudgetTrackerController budgetTrackerController = BudgetTrackerController.getInstance();
        budgetTrackerController.record(20.0);
        budgetTrackerController.record(40.0);
        Assert.assertEquals(60.0, budgetTrackerController.getTotalSpend(), Constants.COMPARISON_DELTA);
    }

    @Test
    public void canAddRecordAndRemovesFunds() throws JSONException, IOException {
        setupTestingDatabase();
        final double budget = 100.0;
        final double spend = 20.0;

        final BudgetTrackerController budgetTrackerController = BudgetTrackerController.getInstance();
        budgetTrackerController.record(spend);
        Assert.assertEquals(budget - spend, budgetTrackerController.getAvailableFunds(), Constants.COMPARISON_DELTA);
    }
}