package model;

import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;

public class BudgetTracker {
    public double monthlyBudget;
    public LocalDate startingDate;
    public int id;

    public BudgetTracker(final double monthlyBudget, final LocalDate startingDate, final int id) {
        this.monthlyBudget = monthlyBudget;
        this.startingDate = startingDate;
        this.id = id;
    }

    public BudgetTracker(final JSONObject json) throws JSONException {
        this.monthlyBudget = json.getDouble("monthly_budget");
        this.startingDate = LocalDate.parse(json.getString("starting_date"));
        this.id = json.getInt("id");
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("monthly_budget", monthlyBudget);
        json.put("starting_date", startingDate.toString());
        json.put("id", id);
        return json;
    }
}
