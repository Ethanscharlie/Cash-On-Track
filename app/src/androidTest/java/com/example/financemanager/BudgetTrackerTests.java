package com.example.financemanager;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.time.LocalDate;

import model.BudgetTracker;

@RunWith(AndroidJUnit4.class)
public class BudgetTrackerTests {
    @Test
    public void canConvertToJson() throws JSONException {
        BudgetTracker tracker = new BudgetTracker(100.30, LocalDate.now(), 0);
        JSONObject json = tracker.toJSON();

        Assert.assertTrue(json.has("monthly_budget"));
        Assert.assertTrue(json.has("starting_date"));
        Assert.assertTrue(json.has("id"));
        Assert.assertEquals(100.30, json.getDouble("monthly_budget"), 0.01);
        Assert.assertEquals(0, json.get("id"));
        Assert.assertEquals(LocalDate.now().toString(), json.getString("starting_date"));
    }

    @Test
    public void canCovertFromJson() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("monthly_budget", 100.12);
        json.put("starting_date", LocalDate.now().toString());
        json.put("id", 40);

        BudgetTracker tracker = new BudgetTracker(json);
        Assert.assertEquals(100.12, tracker.monthlyBudget, 0.01);
        Assert.assertEquals(LocalDate.now(), tracker.startingDate);
        Assert.assertEquals(40, tracker.id);
    }
}
