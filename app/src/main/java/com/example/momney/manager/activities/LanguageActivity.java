package com.example.momney.manager.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

import com.example.momney.manager.R;
import com.example.momney.manager.utils.Utils;

import java.util.Locale;


public class LanguageActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language);
        Utils.restoreState(this);
        checkButton();

    }

    private void checkButton() {
        RadioButton mEnglish, mVietnamese, mChinese, mJapanese;
        mEnglish = findViewById(R.id.en);
        mVietnamese = findViewById(R.id.vi);
        mChinese = findViewById(R.id.zh);
        mJapanese = findViewById(R.id.ja);
        switch (Utils.curLanguage){
            case "en":
                mEnglish.setChecked(true);
                break;
            case "vi":
                mVietnamese.setChecked(true);
                break;
            case "zh":
                mChinese.setChecked(true);
                break;
            case "ja":
                mJapanese.setChecked(true);
                break;
        }
    }

    public void Confirm(View view) {
        Intent intent = new Intent(this, SettingActivity.class);
        startActivity(intent);
    }

    public void switchLanguage(String language){
        Locale locale = new Locale(language);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(
                config,
                getBaseContext().getResources().getDisplayMetrics()
        );
    }


    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        switch (view.getId()) {
            case R.id.en:
                if (checked) {
                    Utils.setLocale(Utils.LANGUAGE[0], this);
                    Utils.curLanguage = Utils.LANGUAGE[0];
                }
                break;
            case R.id.vi:
                if (checked) {
                    Utils.setLocale(Utils.LANGUAGE[1], this);
                    Utils.curLanguage = Utils.LANGUAGE[1];
                }
                break;
            case R.id.zh:
                if (checked) {
                    Utils.setLocale(Utils.LANGUAGE[2], this);
                    Utils.curLanguage = Utils.LANGUAGE[2];
                }
                break;
            case R.id.ja:
                if (checked) {
                    Utils.setLocale(Utils.LANGUAGE[3], this);
                    Utils.curLanguage = Utils.LANGUAGE[3];
                }
            default:
                break;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor preferencesEditor = Utils.mPreferences.edit();
        preferencesEditor.putString(Utils.LANGUAGE_KEY, Utils.curLanguage);
        preferencesEditor.apply();
    }

}