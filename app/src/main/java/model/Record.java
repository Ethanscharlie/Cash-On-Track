package model;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

public class Record {
    public static void addRecord(final double cash, final String tracker) throws JSONException, IOException {
        JSONObject json = new JSONObject();
        json.put("cash", cash);
        json.put("date", LocalDate.now().toString());
        json.put("tracker", tracker);

        Database.getInstance().addItemToTable(json, "records");
    }

    public static double getTotalSpentForTracker(String trackerName) throws JSONException, IOException {
        double total = 0.0;

        ArrayList<JSONObject> records = Database.getInstance().getAllItemsFromTable("records");
        for (JSONObject record : records) {
            if (record.get("name").equals(trackerName)) {
                continue;
            }
            total += record.getDouble("cash");
        }

        return total;
    }

    private Record() {}
}
