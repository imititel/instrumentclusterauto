package com.example.instrumentclusterauto;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class DataTransferFragment extends Fragment {

    private TextView textViewSpeed;
    private TextView textViewTemp;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_data_transfer, container, false);
        textViewSpeed = view.findViewById(R.id.textViewSpeed);
        textViewTemp = view.findViewById(R.id.textViewTemp);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle args = getArguments();
        if (args != null) {
            String speed = args.getString("speed");
            String temp = args.getString("temp");

            textViewSpeed.setText(speed);
            textViewTemp.setText(temp);
        }
    }
}
