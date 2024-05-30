package com.example.instrumentclusterauto;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class DataDisplayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_display);  // Create and use the correct layout

        TextView dataTextView = findViewById(R.id.dataTextView);

        // Get the data passed from DashboardActivity
        String data = getIntent().getStringExtra("data");
        dataTextView.setText(data);
    }
}
