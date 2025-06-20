package model;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Database {
    public static final String DB_FILENAME = "database";
    static Database instance;

    final private File file;
    final private Context context;

    public static void init(final Context context) {
        instance = new Database(context);
    }

    public static Database getInstance() {
        if (instance == null) {
            throw new IllegalStateException("Database is not initialized. Call Database.init(context) first.");
        }

        return instance;
    }

    private Database(final Context context) {
        this.context = context;
        this.file = getPathWithFilename(DB_FILENAME).toFile();
    }

    public void remove(JSONObject itemToRemove, String table) throws Exception {
        final JSONObject json = readJSONFromFile();
        final JSONArray itemsArrayJson = (JSONArray) json.get(table);

        for (int i = 0; i < itemsArrayJson.length(); i ++) {
            final JSONObject item = itemsArrayJson.getJSONObject(i);
            if (item.getString("id").equals(itemToRemove.getString("id"))) {
                itemsArrayJson.remove(i);
                writeStringToFile(json.toString());
                return;
            }
        }

        throw new Exception("Cannot remove item, item not found");
    }

    public void createNewDatabaseOnFilesystem() throws IOException, JSONException {
        file.createNewFile();
        writeStringToFile(generateNewDatabaseJson().toString());
    }

    public void createNewDatabaseOnFilesystemIfMissing() throws IOException, JSONException {
        if (file.exists()) {
            return;
        }

        createNewDatabaseOnFilesystem();
    }

    public void addItemToTable(final JSONObject item, final String tableName) throws Exception {
        throwExpceptionIfJSONHasNoID(item);

        final JSONObject json = readJSONFromFile();
        final JSONArray recordsArrayJson = (JSONArray) json.get(tableName);
        recordsArrayJson.put(item);
        writeStringToFile(json.toString());
    }

    public void throwExpceptionIfJSONHasNoID(final JSONObject json) throws Exception {
        if (json.has("id")) {
            return;
        }

        throw new Exception("Item needs id key");
    }

    public ArrayList<JSONObject> getAllItemsFromTable(final String tableName) throws JSONException, IOException {
        final ArrayList<JSONObject> items = new ArrayList<>();

        final JSONObject json = readJSONFromFile();
        final JSONArray recordsArrayJson = (JSONArray) json.get(tableName);

        for (int i = 0; i < recordsArrayJson.length(); i ++) {
            final JSONObject recordJSON = recordsArrayJson.getJSONObject(i);
            items.add(recordJSON);
        }

        return items;
    }

    private Path getPathWithFilename(final String filename) {
        final File androidDirectory = context.getFilesDir();
        final String androidDirectoryURI = androidDirectory.getParent();
        return Paths.get(androidDirectoryURI, filename);
    }

    private void writeStringToFile(final String str) {
        FileOutputStream fos = null;
        OutputStreamWriter osw = null;
        BufferedWriter writer = null;

        try {
            fos = new FileOutputStream(file);
            osw = new OutputStreamWriter(fos);
            writer = new BufferedWriter(osw);

            writer.write(str);

            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null) writer.close();
                if (osw != null) osw.close();
                if (fos != null) fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private JSONObject readJSONFromFile() throws IOException, JSONException {
        final String jsonRaw = readStringFromFile();
        return new JSONObject(jsonRaw);
    }

    private String readStringFromFile() throws IOException {
        StringBuilder stringBuilder = new StringBuilder();

        FileInputStream fis = new FileInputStream(file);
        int character;
        while ((character = fis.read()) != -1) {
            stringBuilder.append((char) character);
        }
        fis.close();

        return stringBuilder.toString();
    }

    private JSONObject generateNewDatabaseJson() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("records", new JSONArray());
        json.put("tracker", new JSONArray());
        return json;
    }
}
