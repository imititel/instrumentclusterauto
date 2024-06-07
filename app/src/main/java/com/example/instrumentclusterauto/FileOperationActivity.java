package com.example.instrumentclusterauto;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileOperationActivity extends AppCompatActivity {

    private static final String FILE_NAME = "data.json";
    private TextView fileContentTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_operation);

        fileContentTextView = findViewById(R.id.fileContentTextView);
        Button writeButton = findViewById(R.id.writeButton);
        Button readButton = findViewById(R.id.readButton);
        Button addButton = findViewById(R.id.addButton);
        Button deleteButton = findViewById(R.id.deleteButton);

        writeButton.setOnClickListener(v -> writeFile());
        readButton.setOnClickListener(v -> readFile());
        addButton.setOnClickListener(v -> addItem());
        deleteButton.setOnClickListener(v -> deleteFile());
    }

    private void writeFile() {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("make", "Toyota");
            jsonObject.put("model", "Corolla");
            jsonObject.put("year", 2022);

            JSONArray componentsArray = new JSONArray();

            JSONObject engine = new JSONObject();
            engine.put("name", "Engine");
            JSONObject engineDetails = new JSONObject();
            engineDetails.put("type", "1.8L 4-cylinder");
            engineDetails.put("horsepower", 139);
            engineDetails.put("torque", 126);
            engineDetails.put("active", true);
            engine.put("details", engineDetails);
            componentsArray.put(engine);

            JSONObject brakes = new JSONObject();
            brakes.put("name", "Brakes");
            JSONObject brakeDetails = new JSONObject();
            brakeDetails.put("type", "Disc Brakes");
            brakeDetails.put("front", "Ventilated Discs");
            brakeDetails.put("rear", "Solid Discs");
            brakeDetails.put("active", true);
            brakes.put("details", brakeDetails);
            componentsArray.put(brakes);

            JSONObject infotainment = new JSONObject();
            infotainment.put("name", "Infotainment System");
            JSONObject infotainmentDetails = new JSONObject();
            infotainmentDetails.put("screen_size", "8 inches");
            infotainmentDetails.put("connectivity", new JSONArray().put("Bluetooth").put("USB").put("Apple CarPlay").put("Android Auto"));
            infotainmentDetails.put("active", true);
            infotainment.put("details", infotainmentDetails);
            componentsArray.put(infotainment);

            jsonObject.put("components", componentsArray);

            try (FileOutputStream fos = openFileOutput(FILE_NAME, MODE_PRIVATE)) {
                fos.write(jsonObject.toString().getBytes());
            }

            Log.d("FileOperationActivity", "Data written to file");
        } catch (Exception e) {
            Log.e("FileOperationActivity", "Error writing file", e);
        }
    }

    private void readFile() {
        File file = new File(getFilesDir(), FILE_NAME);
        if (file.exists()) {
            try (FileInputStream fis = openFileInput(FILE_NAME)) {
                byte[] buffer = new byte[fis.available()];
                fis.read(buffer);
                String content = new String(buffer);
                fileContentTextView.setText(content);
                Log.d("FileOperationActivity", "Data read from file");
            } catch (IOException e) {
                Log.e("FileOperationActivity", "Error reading file", e);
                fileContentTextView.setText("Error reading file");
            }
        } else {
            Log.d("FileOperationActivity", "File not found");
            fileContentTextView.setText("File not found");
        }
    }

    private void addItem() {
        File file = new File(getFilesDir(), FILE_NAME);
        if (file.exists()) {
            try (FileInputStream fis = openFileInput(FILE_NAME)) {
                byte[] buffer = new byte[fis.available()];
                fis.read(buffer);
                String content = new String(buffer);

                JSONObject jsonObject = new JSONObject(content);
                JSONArray jsonArray = jsonObject.getJSONArray("components");

                JSONObject newComponent = new JSONObject();
                newComponent.put("name", "Battery");
                JSONObject batteryDetails = new JSONObject();
                batteryDetails.put("type", "Lithium-ion");
                batteryDetails.put("capacity", "3.7 kWh");
                batteryDetails.put("active", true);
                newComponent.put("details", batteryDetails);
                jsonArray.put(newComponent);

                jsonObject.put("components", jsonArray);

                try (FileOutputStream fos = openFileOutput(FILE_NAME, MODE_PRIVATE)) {
                    fos.write(jsonObject.toString().getBytes());
                }

                Log.d("FileOperationActivity", "Item added to file");
            } catch (Exception e) {
                Log.e("FileOperationActivity", "Error adding item to file", e);
            }
        } else {
            Log.d("FileOperationActivity", "File not found, cannot add item");
            fileContentTextView.setText("File not found, cannot add item");
        }
    }

    private void deleteFile() {
        boolean deleted = deleteFile(FILE_NAME);
        if (deleted) {
            fileContentTextView.setText("");
            Log.d("FileOperationActivity", "File deleted successfully");
        } else {
            Log.e("FileOperationActivity", "Error deleting file");
            fileContentTextView.setText("Error deleting file");
        }
    }
}
