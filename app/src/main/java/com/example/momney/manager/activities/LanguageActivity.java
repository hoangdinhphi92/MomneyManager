package com.example.momney.manager.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.example.momney.manager.R;
import com.example.momney.manager.SettingActivity;

import java.util.ArrayList;

public class LanguageActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language);

    }

    public void Confirm(View view) {
        Intent intent = new Intent(this, SettingActivity.class);
        startActivity(intent);
    }

}