package com.example.financemanager;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import model.SpendRecordDatabase;

@RunWith(AndroidJUnit4.class)
public class SpendRecordDatabaseTests {
    static final String TEST_DATABASE_FILENAME = "testingDB.json";
    static final Context context = ApplicationProvider.getApplicationContext();

    @Test
    public void canCreateADatabaseOnFilesystem() throws IOException {
        SpendRecordDatabase database = new SpendRecordDatabase(context, TEST_DATABASE_FILENAME);
        database.createNewDatabaseOnFilesystem();

        final File androidDirectory = context.getFilesDir();
        final String androidDirectoryURI = androidDirectory.getParent();
        final Path path = Paths.get(androidDirectoryURI, TEST_DATABASE_FILENAME);
        Assert.assertTrue(path.toFile().exists());
    }
}
