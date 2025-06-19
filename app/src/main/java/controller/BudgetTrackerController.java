package controller;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import model.BudgetTracker;
import model.Database;
import model.SpendRecord;

public class BudgetTrackerController {
  static BudgetTrackerController instance;

  private final Database database;

  public static BudgetTrackerController getInstance() {
    if (instance != null) {
      return instance;
    }

    instance = new BudgetTrackerController();
    return instance;
  }

  private BudgetTrackerController() {
    this.database = Database.getInstance();
  }

  public double getTotalSpend() throws JSONException, IOException {
    double total = 0.0;

    for (JSONObject recordJSON : database.getAllItemsFromTable("records")) {
      SpendRecord record = new SpendRecord(recordJSON);
      total += record.cash;
    }

    return total;
  }

  public int getMonthsElapsed() throws JSONException, IOException {
    final LocalDate start = getFirstTracker().startingDate.withDayOfMonth(1);
    final LocalDate now = LocalDate.now().withDayOfMonth(1);
    final double diff = ChronoUnit.MONTHS.between(start, now);
    return (int) diff;
  }

  public double getAvailableFunds() throws JSONException, IOException {
    final double monthlyBudget = getFirstTracker().monthlyBudget;
    final double totalSpent = getTotalSpend();
    final double monthsElapsed = getMonthsElapsed();
    final double pool = monthlyBudget + (monthlyBudget * monthsElapsed);
    return pool - totalSpent;
  }

  public void record(final double cash) throws JSONException, IOException {
    final SpendRecord record = new SpendRecord(cash, LocalDate.now());
    database.addItemToTable(record.toJSON(), "records");
  }

  private BudgetTracker getFirstTracker() throws JSONException, IOException {
    final JSONObject firstTrackerJSON = database.getAllItemsFromTable("tracker").get(0);
    return new BudgetTracker(firstTrackerJSON);
  }
}

