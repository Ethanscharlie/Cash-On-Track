package model;

import android.content.Context;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class SpendRecordDatabase {
    final private File file;
    final Context context;

    public SpendRecordDatabase(final Context context, final String filename) {
        this.context = context;

        final File androidDirectory = context.getFilesDir();
        final String androidDirectoryURI = androidDirectory.getParent();
        final Path path = Paths.get(androidDirectoryURI, filename);
        this.file = path.toFile();
    }

    public void createNewDatabaseOnFilesystem() throws IOException {
        file.createNewFile();
    }
}
