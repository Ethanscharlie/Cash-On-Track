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
        this.file = getPathWithFilename(filename).toFile();
    }

    public void createNewDatabaseOnFilesystem() throws IOException {
        file.createNewFile();
    }

    private Path getPathWithFilename(final String filename) {
        final File androidDirectory = context.getFilesDir();
        final String androidDirectoryURI = androidDirectory.getParent();
        return Paths.get(androidDirectoryURI, filename);
    }
}
