package com.example.kiwienglish.Information;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.kiwienglish.BuildConfig;
import com.example.kiwienglish.R;

public class InformationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        TextView tv = findViewById(R.id.atvt_information_tv2);
        tv.setText(BuildConfig.VERSION_NAME);
    }
}