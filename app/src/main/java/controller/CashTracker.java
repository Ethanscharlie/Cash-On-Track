package controller;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

import model.SpendRecord;

public class CashTracker {
  final private ArrayList<SpendRecord> records = new ArrayList<>();
  final private double monthlyBudget;
  final private LocalDate startingDate;

  public CashTracker(final double monthlyBudget, final LocalDate startingDate) {
    this.monthlyBudget = monthlyBudget;
    this.startingDate = startingDate;
  }

  public double getTotalSpend() {
    double total = 0.0;

    for (SpendRecord record : records) {
      total += record.cash;
    }

    return total;
  }

  public int getMonthsElapsed() {
    final LocalDate start = startingDate.withDayOfMonth(1);
    final LocalDate now = LocalDate.now().withDayOfMonth(1);
    final double diff;
    diff = ChronoUnit.MONTHS.between(start, now);
    return (int) diff;
  }

  public double getAvailableFunds() {
    final double totalSpent = getTotalSpend();
    final double monthsElapsed = getMonthsElapsed();
    final double pool = monthlyBudget + (monthlyBudget * monthsElapsed);
    return pool - totalSpent;
  }

  public void record(final double cash) {
    records.add(new SpendRecord(cash, LocalDate.now()));
  }
}

