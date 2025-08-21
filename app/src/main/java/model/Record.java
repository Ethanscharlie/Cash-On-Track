package model;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

public class Record {
    public static void addRecord(final double cash, final String tracker, final String note) throws Exception {
        JSONObject json = getJSON(cash, tracker, note);
        Database.getInstance().addItemToTable(json, "records");
    }

    public static JSONObject getJSON(final double cash, final String tracker, final String note) throws JSONException {
        JSONObject json = new JSONObject();
        json.put("note", note);
        json.put("cash", cash);
        json.put("date", LocalDate.now().toString());
        json.put("tracker", tracker);
        json.put("id", generateID(cash, LocalDate.now(), tracker));
        return json;
    }

    public static double getTotalSpentForTracker(String trackerName) throws JSONException, IOException {
        double total = 0.0;

        ArrayList<JSONObject> records = Database.getInstance().getAllItemsFromTable("records");
        for (JSONObject record : records) {
            if (!record.get("tracker").equals(trackerName)) {
                continue;
            }
            total += record.getDouble("cash");
        }

        return total;
    }

    public static void remove(String id) throws Exception {
        ArrayList<JSONObject> records = Database.getInstance().getAllItemsFromTable("records");
        for (JSONObject record : records) {
            if (record.get("id").equals(id)) {
                Database.getInstance().remove(record, "records");
                return;
            }
        }
    }

    private static String generateID(final Double cash, final LocalDate date, final String tracker) {
        String id = "";
        id += cash.toString();
        id += "_";
        id += date.toString();
        id += "_";
        id += tracker;
        return id;
    }

    private Record() {}
}
