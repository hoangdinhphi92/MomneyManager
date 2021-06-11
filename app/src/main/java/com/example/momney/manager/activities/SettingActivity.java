package com.example.momney.manager.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.momney.manager.R;
import com.example.momney.manager.activities.CurrencyActivity;
import com.example.momney.manager.activities.LanguageActivity;
import com.example.momney.manager.activities.MainActivity;
import com.example.momney.manager.data.MoneyDatabase;
import com.example.momney.manager.data.MoneyDatabaseImpl;
import com.example.momney.manager.utils.Utils;

import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Currency;

import dev.shreyaspatil.MaterialDialog.AbstractDialog;
import dev.shreyaspatil.MaterialDialog.BottomSheetMaterialDialog;
import dev.shreyaspatil.MaterialDialog.interfaces.DialogInterface;

import static com.example.momney.manager.utils.Utils.sharedPrefFile;

public class SettingActivity extends AppCompatActivity {

    Button weekbtn, monthbtn, yearbtn;

    private final int FILTER_WEEK = 2;
    private final int FILTER_MONTH = 1;
    private final int FILTER_YEAR = 0;
    EditText amount;
    TextView language, currency, amtCur, mWarning;
    ImageView warning;

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
        switch (Utils.mCurrency) {
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
        switch (Utils.curLanguage) {
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
        warning = findViewById(R.id.warning);
        mWarning = findViewById(R.id.warning_detail);
        amount.setHint(String.valueOf(Utils.weekUse));
        if(Utils.weekUse == 0 || Utils.limitOver(2, this)){
            warning.setVisibility(View.VISIBLE);
            mWarning.setVisibility(View.VISIBLE);
            if(Utils.limitOver(FILTER_WEEK, this)) mWarning.setText(R.string.limit_over);
            if(Utils.weekUse == 0) mWarning.setText(R.string.limit_not_set);
        }
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
        warning.setVisibility(View.INVISIBLE);
        mWarning.setVisibility(View.INVISIBLE);
        weekbtn.setTextColor(ContextCompat.getColor(this, R.color.white));
        monthbtn.setTextColor(ContextCompat.getColor(this, R.color.opa_white));
        yearbtn.setTextColor(ContextCompat.getColor(this, R.color.opa_white));
        amount.setHint(String.valueOf(Utils.weekUse));
        filter = FILTER_WEEK;
        if(Utils.weekUse == 0 || Utils.limitOver(filter, this) ) {
            warning.setVisibility(View.VISIBLE);
            mWarning.setVisibility(View.VISIBLE);
            if (Utils.limitOver(filter, this)) mWarning.setText(R.string.limit_over);
            if (Utils.weekUse == 0) mWarning.setText(R.string.limit_not_set);
        }

    }

    public void ChoseMonth(View view) {
        warning.setVisibility(View.INVISIBLE);
        mWarning.setVisibility(View.INVISIBLE);
        monthbtn.setTextColor(ContextCompat.getColor(this, R.color.white));
        weekbtn.setTextColor(ContextCompat.getColor(this, R.color.opa_white));
        yearbtn.setTextColor(ContextCompat.getColor(this, R.color.opa_white));
        amount.setHint(String.valueOf(Utils.monthUse));
        filter = FILTER_MONTH;
        if( Utils.monthUse == 0 || Utils.limitOver(filter, this) ) {
            warning.setVisibility(View.VISIBLE);
            mWarning.setVisibility(View.VISIBLE);
            if (Utils.limitOver(filter, this)) mWarning.setText(R.string.limit_over);
            if(Utils.monthUse == 0 ) mWarning.setText(R.string.limit_not_set);
        }

    }

    public void ChoseYear(View view) {
        warning.setVisibility(View.INVISIBLE);
        mWarning.setVisibility(View.INVISIBLE);
        yearbtn.setTextColor(ContextCompat.getColor(this, R.color.white));
        monthbtn.setTextColor(ContextCompat.getColor(this, R.color.opa_white));
        weekbtn.setTextColor(ContextCompat.getColor(this, R.color.opa_white));
        amount.setHint(String.valueOf(Utils.yearUse));
        filter = FILTER_YEAR;
        if( Utils.yearUse == 0 || Utils.limitOver(filter, this) ) {
            warning.setVisibility(View.VISIBLE);
            mWarning.setVisibility(View.VISIBLE);
            if (Utils.limitOver(filter, this)) mWarning.setText(R.string.limit_over);
            if (Utils.yearUse == 0) mWarning.setText(R.string.limit_not_set);
        }
    }

    public void Confirm(View view) {
        if (amount.getText().toString().length() != 0) {
            float amt = Float.parseFloat(amount.getText().toString());
            Calendar calendar = Calendar.getInstance();
            long today = calendar.getTimeInMillis();
            NumberFormat format = NumberFormat.getCurrencyInstance();
            format.setMaximumFractionDigits(2);
            format.setCurrency(Currency.getInstance(Utils.mCurrency));
            String strAmt = format.format(amt);
            MoneyDatabase db = new MoneyDatabaseImpl(this);
            if (amt*Utils.ratio() < -(db.totalIncome(today, filter) - db.totalUse(today, filter))) {
                BottomSheetMaterialDialog mBottomSheetDialog = new BottomSheetMaterialDialog.Builder(this)
                        .setTitle(getString(R.string.cancel_title))
                        .setMessage(getString(R.string.cancel_description))
                        .setCancelable(false)
                        .setAnimation(R.raw.error_warning)
                        .setNegativeButton(getString(R.string.confirm), R.drawable.ic_cancel, new BottomSheetMaterialDialog.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                                dialogInterface.dismiss();
                            }
                        })
                        .build();

                // Show Dialog
                mBottomSheetDialog.show();
            } else {
                switch (filter) {
                    case FILTER_WEEK:
                        Utils.weekUse = (int) amt * Utils.ratio();
                        Utils.weekChanged = calendar.get(Calendar.WEEK_OF_YEAR);
                        Toast.makeText(this,
                                getString(R.string.week_alert) + strAmt, Toast.LENGTH_SHORT).show();
                        break;
                    case FILTER_MONTH:
                        Utils.monthUse = (int) amt * Utils.ratio();
                        Utils.monthChanged = calendar.get(Calendar.MONTH);
                        Toast.makeText(this,
                                getString(R.string.month_alert) + strAmt, Toast.LENGTH_SHORT).show();
                        break;
                    case FILTER_YEAR:
                        Utils.yearUse = (int) amt * Utils.ratio();
                        Utils.yearChanged = calendar.get(Calendar.YEAR);
                        Toast.makeText(this,
                                getString(R.string.year_alert) + strAmt, Toast.LENGTH_SHORT).show();

                        break;
                }
                warning.setVisibility(View.INVISIBLE);
                mWarning.setVisibility(View.INVISIBLE);
            }
        }
    }

    private void restoreState() {
        Utils.mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        Utils.curLanguage = Utils.mPreferences.getString(Utils.LANGUAGE_KEY, "vi");
        Utils.setLocale(Utils.curLanguage, this);
        Utils.mCurrency = Utils.mPreferences.getString(Utils.CURRENCY_KEY, "VND");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor preferencesEditor = Utils.mPreferences.edit();
        preferencesEditor.putInt(Utils.WEEK_SET_KEY, Utils.weekUse);
        preferencesEditor.putInt(Utils.MONTH_SET_KEY, Utils.monthUse);
        preferencesEditor.putInt(Utils.YEAR_SET_KEY, Utils.yearUse);
        preferencesEditor.putInt(Utils.WEEK_CHANGE_KEY, Utils.weekChanged);
        preferencesEditor.putInt(Utils.MONTH_CHANGE_KEY, Utils.monthChanged);
        preferencesEditor.putInt(Utils.YEAR_CHANGE_KEY, Utils.yearChanged);
        preferencesEditor.apply();
    }
}