package com.example.instrumentclusterauto;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

public class DataTransferActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_transfer);

        String speed = getIntent().getStringExtra("speed");
        String temp = getIntent().getStringExtra("temp");

        Bundle bundle = new Bundle();
        bundle.putString("speed", speed);
        bundle.putString("temp", temp);

        DataTransferFragment fragment = new DataTransferFragment();
        fragment.setArguments(bundle);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }
}
