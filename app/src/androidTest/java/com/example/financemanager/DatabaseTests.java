package com.example.financemanager;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;

import model.Record;
import model.Database;

@RunWith(AndroidJUnit4.class)
public class DatabaseTests {
    static final Context context = ApplicationProvider.getApplicationContext();

    private Path getPathForDatabase() {
        final File androidDirectory = context.getFilesDir();
        final String androidDirectoryURI = androidDirectory.getParent();
        return Paths.get(androidDirectoryURI, Database.DB_FILENAME);
    }

    private static JSONObject readJsonFromFile(File file) {
        StringBuilder jsonString = new StringBuilder();

        try (FileInputStream fis = new FileInputStream(file);
             InputStreamReader isr = new InputStreamReader(fis);
             BufferedReader reader = new BufferedReader(isr)) {

            String line;
            while ((line = reader.readLine()) != null) {
                jsonString.append(line);
            }

            // Parse the string into a JSONObject
            return new JSONObject(jsonString.toString());

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Test
    public void canCreateADatabaseOnFilesystem() throws IOException, JSONException {
        Database.init(context);
        Database database = Database.getInstance();
        database.createNewDatabaseOnFilesystem();

        Assert.assertTrue(getPathForDatabase().toFile().exists());
    }

    @Test
    public void databaseContainsJsonWithDirs() throws IOException, JSONException {
        Database.init(context);
        Database database = Database.getInstance();
        database.createNewDatabaseOnFilesystem();

        File file = getPathForDatabase().toFile();
        JSONObject json = readJsonFromFile(file);

        Assert.assertNotEquals(null, json);
        Assert.assertTrue(json.has("records"));
        Assert.assertTrue(json.has("tracker"));
    }

    @Test
    public void canRemoveItemFromTable() throws Exception {
        Database.init(context);
        Database database = Database.getInstance();
        database.createNewDatabaseOnFilesystem();

        Record.addRecord(100, "foo");
        JSONObject json = Record.getJSON(100, "foo");
        Record.addRecord(100, "bar");

        database.remove(json, "records");

        final ArrayList<JSONObject> records = Database.getInstance().getAllItemsFromTable("records");
        Assert.assertEquals(1, records.size());
        Assert.assertEquals("bar", records.get(0).getString("tracker"));
    }
}
