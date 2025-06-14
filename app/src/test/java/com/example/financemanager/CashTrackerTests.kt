package com.example.financemanager

import model.COMPARISON_DELTA
import model.CashTracker
import org.junit.Test

import org.junit.Assert.*
import java.time.LocalDate

class DoubleComparisonTests {
    @Test
    fun comparisonIsCloseEnough() {
        val x = 100.0;
        val y = 100.0;
        assertEquals(x, y, COMPARISON_DELTA)
    }

    @Test
    fun comparisonIsToFar() {
        val x = 99.99;
        val y = 100.0;
        assertNotEquals(x, y, COMPARISON_DELTA)
    }
}

class TrackerTests {
    @Test
    fun testGetMonthsElapsedOverManyMonths() {
        val addingMonths = 20

        val future = LocalDate.now().minusMonths(20)
        val cashTracker = CashTracker(100.0, future)
        val monthsElapsed = cashTracker.getMonthsElapsed()
        assertEquals(addingMonths, monthsElapsed)
    }

    @Test
    fun testGetMonthsElapsedOverNoMonths() {
        val cashTracker = CashTracker(100.0, LocalDate.now())
        val monthsElapsed = cashTracker.getMonthsElapsed()
        assertEquals(0, monthsElapsed)
    }

    @Test
    fun trackerPoolIsCorrectOnInit() {
        val cashTracker = CashTracker(100.0, LocalDate.now())
        val availableFunds = cashTracker.getAvailableFunds()
        assertEquals(100.0, availableFunds, COMPARISON_DELTA)
    }

    @Test
    fun canCollectAllRecords() {
        val cashTracker = CashTracker(100.0, LocalDate.now())
        cashTracker.record(20.0)
        cashTracker.record(40.0)
        assertEquals(60.0, cashTracker.getTotalSpend(), COMPARISON_DELTA)
    }

    @Test
    fun canAddRecordAndRemovesFunds() {
        val budget = 100.0;
        val spend = 20.0;

        val cashTracker = CashTracker(budget, LocalDate.now())
        cashTracker.record(spend)
        assertEquals(budget - spend, cashTracker.getAvailableFunds(), COMPARISON_DELTA)
    }
}