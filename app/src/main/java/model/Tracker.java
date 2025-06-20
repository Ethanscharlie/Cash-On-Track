package model;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class Tracker {
    public static final String MONTHLY = "monthly";
    public static final String WEEKLY = "weekly";

    public static void addTracker(String name, String periodType, double cash) throws Exception {
        if (findTracker(name) != null) {
            System.out.printf("Tracker %s already exists\n", name);
            return;
        }

        final JSONObject json = createJson(name, periodType, cash);
        Database.getInstance().addItemToTable(json, "tracker");
    }

    public static ArrayList<String> getAvailableTrackers() throws JSONException, IOException {
        final ArrayList<String> availableTrackers = new ArrayList<>();

        final ArrayList<JSONObject> trackers = getAllTrackers();
        for (final JSONObject tracker : trackers) {
            availableTrackers.add(tracker.getString("name"));
        }

        return availableTrackers;
    }

    public static double getBalanceOfTracker(String trackerName) throws Exception {
        return getBalanceOfTrackerForDate(trackerName, LocalDate.now());
    }

    public static double getBalanceOfTrackerForDate(final String trackerName,
                                                    final LocalDate currentDate) throws Exception {
        JSONObject tracker = findTracker(trackerName);
        throwExceptionIfTrackerIsNull(tracker);

        final double cash = tracker.getDouble("cash");
        final int periods = getPeriodsFromTracker(tracker, currentDate);
        final double totalSpent = Record.getTotalSpentForTracker(trackerName);

        final double pool = cash + (cash * periods);
        return pool - totalSpent;
    }

    private static int getPeriodsFromTracker(final JSONObject tracker,
                                             final LocalDate currentDate) throws Exception {
        final String startinDateString = tracker.getString("starting_date");
        final LocalDate startingDate = LocalDate.parse(startinDateString);
        final String periodType = tracker.getString("period_type");

        return getPeriods(startingDate, periodType, currentDate);
    }

    private static int getPeriods(final LocalDate startingDate,
                                  final String periodType,
                                  final LocalDate currentDate) throws Exception {
        switch (periodType) {
            case WEEKLY:
            {
                final LocalDate now = LocalDate.now();
                final double diff = ChronoUnit.WEEKS.between(startingDate, currentDate);
                return (int) diff;
            }
            case MONTHLY:
            {
                final LocalDate start = startingDate.withDayOfMonth(1);
                final LocalDate now = currentDate.withDayOfMonth(1);
                final double diff = ChronoUnit.MONTHS.between(start, now);
                return (int) diff;
            }
        }

        throw new Exception("Cannot calculate period for non existent period type");
    }

    private static void throwExceptionIfTrackerIsNull(final JSONObject tracker) throws Exception {
        final boolean trackerExists = tracker != null;
        if (!trackerExists) {
            throw new Exception("Tracker does not exist");
        }
    }

    private static JSONObject createJson(String name, String periodType, double cash) throws Exception {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("starting_date", LocalDate.now().toString());
        json.put("period_type", periodType);
        json.put("cash", cash);
        return json;
    }

    private static ArrayList<JSONObject> getAllTrackers() throws JSONException, IOException {
        final Database database = Database.getInstance();
        return database.getAllItemsFromTable("tracker");
    }

    private static boolean trackersNameIs(JSONObject tracker, String trackerName) throws JSONException {
        return tracker.get("name").equals(trackerName);
    }

    private static JSONObject findTracker(String trackerName) throws JSONException, IOException {
        final ArrayList<JSONObject> trackers = getAllTrackers();
        for (final JSONObject tracker : trackers) {
            if (trackersNameIs(tracker, trackerName)) {
                return tracker;
            }
        }

        return null;
    }
}
