package com.example.momney.manager.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

import com.example.momney.manager.R;
import com.example.momney.manager.SettingActivity;
import com.example.momney.manager.activities.MainActivity;
import com.example.momney.manager.utils.Utils;

public class CurrencyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency);
        checkButton();
        Utils.restoreState(this);

    }

    public void Confirm(View view) {
        Intent intent = new Intent(this, SettingActivity.class);
        startActivity(intent);
    }

    private void checkButton() {
        RadioButton usd, vnd, cny, jpy;
        usd = findViewById(R.id.usd);
        vnd = findViewById(R.id.vnd);
        cny = findViewById(R.id.rbm);
        jpy = findViewById(R.id.jpy);
        switch (Utils.mCurrency){
            case "USD":
                usd.setChecked(true);
                break;
            case "VND":
                vnd.setChecked(true);
                break;
            case "CNY":
                cny.setChecked(true);
                break;
            case "JPY":
                jpy.setChecked(true);
                break;
        }
    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.usd:
                if (checked) {
                    Utils.mCurrency = Utils.currency[0];
                }
                break;
            case R.id.vnd:
                if (checked) {
                    Utils.mCurrency = Utils.currency[1];
                }
                break;
            case R.id.rbm:
                if (checked) {
                    Utils.mCurrency = Utils.currency[2];
                }
                break;
            case R.id.jpy:
                if (checked) {
                    Utils.mCurrency = Utils.currency[3];
                }
            default:
                break;
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor preferencesEditor = Utils.mPreferences.edit();
        preferencesEditor.putString(Utils.CURRENCY_KEY, Utils.mCurrency);
        preferencesEditor.apply();
    }

}