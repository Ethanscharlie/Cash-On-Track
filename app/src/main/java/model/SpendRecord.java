package model;

import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;

public class SpendRecord {
    public double cash;
    public LocalDate date;

    public SpendRecord(final double cash, final LocalDate date) {
        this.cash = cash;
        this.date = date;
    }

    public SpendRecord(final JSONObject json) throws JSONException {
        final int cashRaw = (int) json.get("cash");
        final String dateRaw = (String) json.get("date");
        this.cash = cashRaw;
        this.date = LocalDate.parse(dateRaw);
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("cash", cash);
        json.put("date", date.toString());
        return json;
    }
}
