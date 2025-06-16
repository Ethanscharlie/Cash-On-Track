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
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;

import model.SpendRecordDatabase;

@RunWith(AndroidJUnit4.class)
public class SpendRecordDatabaseTests {
    static final String TEST_DATABASE_FILENAME = "testingDB.json";
    static final Context context = ApplicationProvider.getApplicationContext();

    private Path getPathForDatabase() {
        final File androidDirectory = context.getFilesDir();
        final String androidDirectoryURI = androidDirectory.getParent();
        return Paths.get(androidDirectoryURI, TEST_DATABASE_FILENAME);
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
        SpendRecordDatabase database = new SpendRecordDatabase(context, TEST_DATABASE_FILENAME);
        database.createNewDatabaseOnFilesystem();

        Assert.assertTrue(getPathForDatabase().toFile().exists());
    }

    @Test
    public void databaseContainsJsonWithDir() throws IOException, JSONException {
        SpendRecordDatabase database = new SpendRecordDatabase(context, TEST_DATABASE_FILENAME);
        database.createNewDatabaseOnFilesystem();

        File file = getPathForDatabase().toFile();
        JSONObject json = readJsonFromFile(file);

        Assert.assertNotEquals(null, json);
        Assert.assertTrue(json.has("records"));
    }
}
