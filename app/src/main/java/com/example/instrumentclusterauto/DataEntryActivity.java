package com.example.instrumentclusterauto;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.IOException;

public class DataEntryActivity extends AppCompatActivity {

    private EditText editTextId;
    private EditText editTextName;
    private static final String FILE_NAME = "data.txt";
    private static final String TAG = "DataEntryActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_entry);

        editTextId = findViewById(R.id.editTextId);
        editTextName = findViewById(R.id.editTextName);
        Button buttonSave = findViewById(R.id.buttonSave);
        Button buttonDelete = findViewById(R.id.buttonDelete);

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteData();
            }
        });

        // Citirea și afișarea datelor la deschiderea activității
        readData();
    }

    private void saveData() {
        String id = editTextId.getText().toString();
        String name = editTextName.getText().toString();

        if ((id.contains("check") || name.contains("check")) && !id.equals("check") && !name.equals("check")) {
            if (!id.isEmpty() && !name.isEmpty()) {
                String data = id.replace("check", "") + " - " + name.replace("check", "") + "\n";
                FileOutputStream fos = null;

                try {
                    fos = openFileOutput(FILE_NAME, MODE_APPEND);
                    fos.write(data.getBytes());
                    editTextId.setText("");
                    editTextName.setText("");
                    Log.d(TAG, "Data saved successfully");
                } catch (IOException e) {
                    Log.e(TAG, "Error saving data", e);
                } finally {
                    if (fos != null) {
                        try {
                            fos.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            } else {
                Log.d(TAG, "Please enter both ID and Name");
            }
        } else {
            Log.d(TAG, "Data must contain the word 'check'");
        }

        // Re-citirea și afișarea datelor după salvare
        readData();
    }

    private void deleteData() {
        String idToDelete = editTextId.getText().toString();

        if (idToDelete.contains("delete") && !idToDelete.equals("delete")) {
            if (!idToDelete.isEmpty()) {
                StringBuilder newData = new StringBuilder();
                boolean found = false;

                try {
                    FileInputStream fis = openFileInput(FILE_NAME);
                    BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
                    String line;

                    while ((line = reader.readLine()) != null) {
                        if (line.startsWith(idToDelete.replace("delete", "") + " - ")) {
                            found = true;
                        } else {
                            newData.append(line).append("\n");
                        }
                    }

                    reader.close();
                    fis.close();

                    if (found) {
                        FileOutputStream fos = openFileOutput(FILE_NAME, MODE_PRIVATE);
                        fos.write(newData.toString().getBytes());
                        fos.close();
                        Log.d(TAG, "Data deleted successfully");
                    } else {
                        Log.d(TAG, "ID not found");
                    }

                } catch (IOException e) {
                    Log.e(TAG, "Error deleting data", e);
                }

            } else {
                Log.d(TAG, "Please enter ID to delete");
            }
        } else {
            Log.d(TAG, "ID must contain the word 'delete'");
        }

        // Re-citirea și afișarea datelor după ștergere
        readData();
    }

    private void readData() {
        try {
            FileInputStream fis = openFileInput(FILE_NAME);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
            StringBuilder result = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                result.append(line).append("\n");
            }

            Log.d(TAG, "Data read successfully:\n" + result.toString());
            reader.close();
            fis.close();
        } catch (IOException e) {
            Log.e(TAG, "Error reading data", e);
        }
    }
}
