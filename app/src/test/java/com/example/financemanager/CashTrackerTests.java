package com.example.financemanager;

import model.Constants;
import controller.CashTracker;
import org.junit.Test;
import org.junit.Assert;

import java.time.LocalDate;

public class CashTrackerTests {
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
    public void testGetMonthsElapsedOverManyMonths() {
        final int addingMonths = 20;

        final LocalDate future = LocalDate.now().minusMonths(20);
        final CashTracker cashTracker = new CashTracker(100.0, future);
        final int monthsElapsed = cashTracker.getMonthsElapsed();
        Assert.assertEquals(addingMonths, monthsElapsed);
    }

    @Test
    public void testGetMonthsElapsedOverNoMonths() {
        final CashTracker cashTracker = new CashTracker(100.0, LocalDate.now());
        final int monthsElapsed = cashTracker.getMonthsElapsed();
        Assert.assertEquals(0, monthsElapsed);
    }

    @Test
    public void trackerPoolIsCorrectOnInit() {
        final CashTracker cashTracker = new CashTracker(100.0, LocalDate.now());
        final double availableFunds = cashTracker.getAvailableFunds();
        Assert.assertEquals(100.0, availableFunds, Constants.COMPARISON_DELTA);
    }

    @Test
    public void canCollectAllRecords() {
        final CashTracker cashTracker = new CashTracker(100.0, LocalDate.now());
        cashTracker.record(20.0);
        cashTracker.record(40.0);
        Assert.assertEquals(60.0, cashTracker.getTotalSpend(), Constants.COMPARISON_DELTA);
    }

    @Test
    public void canAddRecordAndRemovesFunds() {
        final double budget = 100.0;
        final double spend = 20.0;

        final CashTracker cashTracker = new CashTracker(budget, LocalDate.now());
        cashTracker.record(spend);
        Assert.assertEquals(budget - spend, cashTracker.getAvailableFunds(), Constants.COMPARISON_DELTA);
    }
}