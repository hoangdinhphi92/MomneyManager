package com.example.momney.manager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.momney.manager.activities.CurrencyActivity;
import com.example.momney.manager.activities.LanguageActivity;
import com.example.momney.manager.activities.MainActivity;
import com.example.momney.manager.utils.Utils;

import java.text.NumberFormat;
import java.util.Currency;
import static com.example.momney.manager.utils.Utils.sharedPrefFile;

public class SettingActivity extends AppCompatActivity {

    Button weekbtn, monthbtn, yearbtn;

    private final int FILTER_WEEK = 0;
    private final int FILTER_MONTH = 1;
    private final int FILTER_YEAR = 2;
    EditText amount;
    TextView language, currency, amtCur;

    private int filter = FILTER_WEEK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        restoreState();
        map();
        setCurLanguage();
        setCurrency();
    }

    private void setCurrency() {
        switch (Utils.mCurrency){
            case "USD":
                currency.setText(R.string.usd);
                break;
            case "VND":
                currency.setText(R.string.vnd);
                break;
            case "CNY":
                currency.setText(R.string.rmb);
                break;
            case "JPY":
                currency.setText(R.string.yen);
                break;
        }
    }



    private void setCurLanguage() {
        switch (Utils.curLanguage){
            case "en":
                language.setText(R.string.english);
                break;
            case "vi":
                language.setText(R.string.vietnam);
                break;
            case "zh":
                language.setText(R.string.chinese);
                break;
            case "ja":
                language.setText(R.string.japanese);
                break;
        }
    }

    private void map() {
        weekbtn = findViewById(R.id.week_button);
        monthbtn = findViewById(R.id.month_button);
        yearbtn = findViewById(R.id.year_button);
        amount = findViewById(R.id.amount);
        language = findViewById(R.id.cur_language);
        currency = findViewById(R.id.cur_currency);
        amtCur = findViewById(R.id.currency);
        amtCur.setText(Utils.mCurrency);
    }

    public void Return(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void ChoseLanguage(View view) {
        Intent intent = new Intent(this, LanguageActivity.class);
        startActivity(intent);
    }

    public void ChoseCurrency(View view) {
        Intent intent = new Intent(this, CurrencyActivity.class);
        startActivity(intent);
    }

    public void ChoseWeek(View view) {
        weekbtn.setTextColor(ContextCompat.getColor(this, R.color.white));
        monthbtn.setTextColor(ContextCompat.getColor(this, R.color.opa_white));
        yearbtn.setTextColor(ContextCompat.getColor(this, R.color.opa_white));
        filter = FILTER_WEEK;
    }

    public void ChoseMonth(View view) {
        monthbtn.setTextColor(ContextCompat.getColor(this, R.color.white));
        weekbtn.setTextColor(ContextCompat.getColor(this, R.color.opa_white));
        yearbtn.setTextColor(ContextCompat.getColor(this, R.color.opa_white));
        filter = FILTER_MONTH;
    }

    public void ChoseYear(View view) {
        yearbtn.setTextColor(ContextCompat.getColor(this, R.color.white));
        monthbtn.setTextColor(ContextCompat.getColor(this, R.color.opa_white));
        weekbtn.setTextColor(ContextCompat.getColor(this, R.color.opa_white));
        filter = FILTER_YEAR;
    }

    public void Confirm(View view) {
        if(amount.getText().toString().length()!=0){
            float amt = Float.parseFloat(amount.getText().toString());

            NumberFormat format = NumberFormat.getCurrencyInstance();
            format.setMaximumFractionDigits(2);
            format.setCurrency(Currency.getInstance(Utils.mCurrency));
            String strAmt = format.format(amt);
            switch (filter){
                case FILTER_WEEK:
                    Toast.makeText(this,
                            getString(R.string.week_alert) + strAmt , Toast.LENGTH_SHORT).show();
                    break;
                case FILTER_MONTH:
                    Toast.makeText(this,
                            getString(R.string.month_alert) + strAmt, Toast.LENGTH_SHORT).show();
                    break;
                case FILTER_YEAR:
                    Toast.makeText(this,
                            getString(R.string.year_alert) + strAmt, Toast.LENGTH_SHORT).show();

                    break;
            }
        }
    }
    private void restoreState() {
        Utils.mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        Utils.curLanguage = Utils.mPreferences.getString(Utils.LANGUAGE_KEY, "vi");
        Utils.setLocale(Utils.curLanguage, this);
        Utils.mCurrency = Utils.mPreferences.getString(Utils.CURRENCY_KEY, "VND") ;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}