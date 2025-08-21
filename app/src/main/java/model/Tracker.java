package model;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Locale;

public class Tracker {
    public static final String MONTHLY = "monthly";
    public static final String WEEKLY = "weekly";
    public static final String YEARLY = "yearly";

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

    public static void remove(String trackerName) throws Exception {
        final ArrayList<JSONObject> trackers = getAllTrackers();
        for (final JSONObject tracker : trackers) {
            if (trackersNameIs(tracker, trackerName)) {
                Database.getInstance().remove(tracker, "tracker");
                return;
            }
        }
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
                LocalDate start = startingDate.with(TemporalAdjusters.previousOrSame(java.time.DayOfWeek.SUNDAY));
                LocalDate now = currentDate.with(TemporalAdjusters.previousOrSame(java.time.DayOfWeek.SUNDAY));
                final double diff = ChronoUnit.WEEKS.between(start, now);
                return (int) diff;
            }

            case MONTHLY:
            {
                final LocalDate start = startingDate.withDayOfMonth(1);
                final LocalDate now = currentDate.withDayOfMonth(1);
                final double diff = ChronoUnit.MONTHS.between(start, now);
                return (int) diff;
            }

            case YEARLY:
            {
                final LocalDate start = startingDate.withDayOfYear(1);
                final LocalDate now = currentDate.withDayOfYear(1);
                final double diff = ChronoUnit.YEARS.between(start, now);
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
        json.put("id", generateID(cash, LocalDate.now(), name));
        return json;
    }

    private static ArrayList<JSONObject> getAllTrackers() throws JSONException, IOException {
        final Database database = Database.getInstance();
        return database.getAllItemsFromTable("tracker");
    }

    private static boolean trackersNameIs(JSONObject tracker, String trackerName) throws JSONException {
        return tracker.get("name").equals(trackerName);
    }

    public static JSONObject findTracker(String trackerName) throws JSONException, IOException {
        final ArrayList<JSONObject> trackers = getAllTrackers();
        for (final JSONObject tracker : trackers) {
            if (trackersNameIs(tracker, trackerName)) {
                return tracker;
            }
        }

        return null;
    }

    private static String generateID(final Double cash, final LocalDate date, final String name) {
        String id = "";
        id += cash.toString();
        id += "_";
        id += date.toString();
        id += "_";
        id += name;
        return id;
    }

    public static boolean isValidDouble(String number) {
        if (number == null || number.isEmpty()) {
            return false;
        }

        try
        {
            Double.parseDouble(number);
        }
        catch(NumberFormatException e)
        {
            return false;
        }

        return true;
    }

    public static String getTrackerInfoString(String trackerName) throws JSONException, IOException {
        JSONObject tracker = findTracker(trackerName);
        final String periodType = tracker.getString("period_type");
        final double cash = tracker.getDouble("cash");
        return String.format(Locale.ENGLISH, "$ %.2f %s", cash, periodType);
    }
}
