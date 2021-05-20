package com.example.momney.manager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.momney.manager.activities.LanguageActivity;
import com.example.momney.manager.activities.MainActivity;

public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting2);
    }

    public void Return(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void ChoseLanguage(View view) {
        Intent intent = new Intent(this, LanguageActivity.class);
        startActivity(intent);
    }
}