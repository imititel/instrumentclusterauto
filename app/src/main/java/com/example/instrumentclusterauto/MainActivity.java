package com.example.instrumentclusterauto;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private List<DigitalDashboard> dashboards = new ArrayList<>();
    private TextView resultTextView;
    private TextView speedometerTextView;
    private TextView fuelLevelTextView;
    private TextView tempTextView;
    private TextView rpmTextView;
    private TextView clockTextView;
    private ScheduledExecutorService scheduler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find views by their IDs
        EditText minSpeedEditText = findViewById(R.id.minSpeedEditText);
        EditText maxTempEditText = findViewById(R.id.maxTempEditText);
        resultTextView = findViewById(R.id.resultTextView);
        minSpeedEditText.setHintTextColor(getResources().getColor(android.R.color.white));
        maxTempEditText.setHintTextColor(getResources().getColor(android.R.color.white));

        // Initialize dashboards in a background thread
        Executors.newSingleThreadExecutor().execute(this::initializeDashboards);

        // Set up conditions button
        setSubmitConditionsButton(minSpeedEditText, maxTempEditText);

        // Initialize main activity views
        speedometerTextView = findViewById(R.id.speedometerTextView);
        fuelLevelTextView = findViewById(R.id.fuelLevelTextView);
        tempTextView = findViewById(R.id.tempTextView);
        rpmTextView = findViewById(R.id.rpmTextView);
        clockTextView = findViewById(R.id.clockTextView);

        // Start updating background data
        startDataUpdate();

        // Set up button to open DashboardActivity
        FloatingActionButton openDashboardButton = findViewById(R.id.openDashboardButton);
        openDashboardButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, DashboardActivity.class);
            startActivity(intent);
        });

        // Set up button to open DataEntryActivity
        FloatingActionButton openDataEntryButton = findViewById(R.id.openDataEntryButton);
        openDataEntryButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, DataEntryActivity.class);
            startActivity(intent);
        });

        // Set up button to open InternationalizationActivity
        FloatingActionButton openInternationalizationButton = findViewById(R.id.openInternationalizationButton);
        openInternationalizationButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, InternationalizationActivity.class);
            startActivity(intent);
        });

        // Set up button to open DataTransferActivity
        FloatingActionButton openDataTransferButton = findViewById(R.id.openDataTransferButton);
        openDataTransferButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, DataTransferActivity.class);
            String speed = speedometerTextView.getText().toString();
            String temp = tempTextView.getText().toString();
            intent.putExtra("speed", speed);
            intent.putExtra("temp", temp);
            startActivity(intent);
        });
    }

    private void initializeDashboards() {
        dashboards.add(new DigitalDashboard(120, 50, 85, 3000, "12:30"));
        dashboards.add(new DigitalDashboard(80, 30, 90, 1500, "13:45"));
        dashboards.add(new DigitalDashboard(160, 80, 75, 3500, "14:20"));
        dashboards.add(new DigitalDashboard(100, 60, 65, 2200, "15:15"));
        dashboards.add(new DigitalDashboard(90, 40, 95, 1800, "16:00"));
        dashboards.add(new DigitalDashboard(130, 55, 70, 2900, "16:45"));
    }

    private void setSubmitConditionsButton(EditText minSpeedEditText, EditText maxTempEditText) {
        findViewById(R.id.submitConditionsButton).setOnClickListener(view -> {
            try {
                displayFilteredDashboards(minSpeedEditText, maxTempEditText);
            } catch (NumberFormatException e) {
                runOnUiThread(() -> resultTextView.setText("Please enter valid numbers."));
            }
        });
    }

    private void displayFilteredDashboards(EditText minSpeedEditText, EditText maxTempEditText) {
        try {
            int minSpeed = Integer.parseInt(minSpeedEditText.getText().toString());
            int maxTemp = Integer.parseInt(maxTempEditText.getText().toString());

            StringBuilder result = new StringBuilder();
            for (DigitalDashboard dashboard : dashboards) {
                if (dashboard.getSpeedometer() >= minSpeed && dashboard.getEngineTemp() <= maxTemp) {
                    result.append(dashboard.displayInfo()).append("\n");
                }
            }
            runOnUiThread(() -> resultTextView.setText(result.toString()));
        } catch (NumberFormatException e) {
            runOnUiThread(() -> resultTextView.setText("Please enter valid numbers."));
        }
    }

    private void startDataUpdate() {
        scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(this::updateBackgroundData, 0, 1, TimeUnit.SECONDS);
    }

    private void updateBackgroundData() {
        Random random = new Random();
        final int speed = random.nextInt(200);
        final int fuelLevel = random.nextInt(100);
        final int temp = random.nextInt(120);
        final int rpm = random.nextInt(8000);
        final String clock = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());

        runOnUiThread(() -> {
            speedometerTextView.setText("Speed: " + speed + " km/h");
            fuelLevelTextView.setText("Fuel Level: " + fuelLevel + "%");
            tempTextView.setText("Temperature: " + temp + "°C");
            rpmTextView.setText("RPM: " + rpm);
            clockTextView.setText("Clock: " + clock);
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (scheduler != null) {
            scheduler.shutdownNow();
        }
    }
}
