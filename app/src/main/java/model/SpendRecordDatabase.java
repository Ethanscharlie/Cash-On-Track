package model;

import android.content.Context;
import android.util.JsonWriter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class SpendRecordDatabase {
    final private File file;
    final Context context;

    public SpendRecordDatabase(final Context context, final String filename) {
        this.context = context;
        this.file = getPathWithFilename(filename).toFile();
    }

    public void createNewDatabaseOnFilesystem() throws IOException, JSONException {
        file.createNewFile();
        writeStringToFile(generateNewDatabaseJson().toString());
    }

    public void addRecord(final SpendRecord record) throws JSONException, IOException {
        final JSONObject json = readJSONFromFile();
        final JSONArray recordsArrayJson = (JSONArray) json.get("records");
        final JSONObject recordJSON = record.toJSON();

        recordsArrayJson.put(recordJSON);
        writeStringToFile(json.toString());
    }

    public ArrayList<SpendRecord> getAllRecords() throws JSONException, IOException {
        final ArrayList<SpendRecord> records = new ArrayList<>();

        final JSONObject json = readJSONFromFile();
        final JSONArray recordsArrayJson = (JSONArray) json.get("records");

        for (int i = 0; i < recordsArrayJson.length(); i ++) {
            final JSONObject recordJSON = recordsArrayJson.getJSONObject(i);
            records.add(new SpendRecord(recordJSON));
        }

        return records;
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
        return json;
    }
}
