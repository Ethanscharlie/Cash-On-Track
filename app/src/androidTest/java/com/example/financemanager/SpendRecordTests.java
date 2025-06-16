package com.example.financemanager;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.time.LocalDate;

import model.SpendRecord;

@RunWith(AndroidJUnit4.class)
public class SpendRecordTests {
    @Test
    public void canConvertToJson() throws JSONException {
        SpendRecord record = new SpendRecord(15, LocalDate.now());
        JSONObject json = record.toJSON();

        Assert.assertTrue(json.has("cash"));
        Assert.assertTrue(json.has("date"));
        Assert.assertEquals(15.0, json.get("cash"));
        Assert.assertEquals(LocalDate.now().toString(), json.get("date"));
    }

    @Test
    public void canCovertFromJson() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("cash", 10);
        json.put("date", LocalDate.now().toString());

        SpendRecord record = new SpendRecord(json);
        Assert.assertEquals(10, record.cash, 0.01);
        Assert.assertEquals(LocalDate.now().toString(), record.date.toString());
    }
}
