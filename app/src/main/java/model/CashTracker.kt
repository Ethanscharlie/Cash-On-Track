package model

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.Instant
import java.time.LocalDate
import java.time.Period
import java.time.ZoneId
import java.time.temporal.ChronoUnit
import java.util.Calendar
import java.util.Date

class CashTracker(private val monthlyBudget: Double, private val startingDate: LocalDate) {
    val records = ArrayList<SpendRecord>()

    fun getTotalSpend(): Double {
        var total = 0.0

        for (record in records) {
            total += record.cash
        }

        return total
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getMonthsElapsed(): Int {
        val start = startingDate.withDayOfMonth(1)
        val now = LocalDate.now().withDayOfMonth(1)
        val diff = ChronoUnit.MONTHS.between(start, now)
        return diff.toInt()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getAvailableFunds(): Double {
        val totalSpent = getTotalSpend()
        val monthsElapsed = getMonthsElapsed()
        val pool = monthlyBudget + (monthlyBudget * monthsElapsed)
        return pool - totalSpent
    }

    fun record(cash: Double) {
        records.add(SpendRecord(cash))
    }
}