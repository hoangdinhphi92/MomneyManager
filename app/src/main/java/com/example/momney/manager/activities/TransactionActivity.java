package com.example.momney.manager.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import com.example.momney.manager.ChoseDate;
import com.example.momney.manager.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TransactionActivity extends AppCompatActivity {

    private boolean choseInc;

    private static final String DATE_FORMAT = "dd/MM/yyyy";

    Button mIncome;
    Button mExpense;
    View mView;
    TextView mDate;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);
        mView = findViewById(R.id.view);
        mIncome = findViewById(R.id.income_button);
        mExpense = findViewById(R.id.year_button);
        mDate = findViewById(R.id.date);
        mDate.setText("Today, " + getDateString(Calendar.getInstance().getTime())) ;

    }

    public static String getDateString(Date date) {
        @SuppressLint ("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
        return format.format(date);}

    public void Return(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void ChoseIncome(View view) {
        choseInc = true;
        mView.setBackground(ContextCompat.getDrawable(this, R.drawable.income_background));
        mIncome.setTextColor(ContextCompat.getColor(this, R.color.white));
        mExpense.setTextColor(ContextCompat.getColor(this, R.color.opa_white));
    }

    public void ChoseExpense(View view) {
        choseInc = false;
        mView.setBackground(ContextCompat.getDrawable(this, R.drawable.expense_background));
        mIncome.setTextColor(ContextCompat.getColor(this, R.color.opa_white));
        mExpense.setTextColor(ContextCompat.getColor(this, R.color.white));
    }

    public void ChoseDate(View view) {
        DialogFragment newFragment = new ChoseDate();
        newFragment.show(getSupportFragmentManager(),"datePicker");
    }

    @SuppressLint("SetTextI18n")
    public void processDatePickerResult(int year, int month, int day) {
        String month_string = Integer.toString(month + 1);
        String day_string = Integer.toString(day);
        String year_string = Integer.toString(year);
        mDate.setText(day_string + "/" + month_string + "/" + year_string);

    }
}
