package com.example.momney.manager.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import com.bumptech.glide.Glide;
import com.example.momney.manager.ChoseDate;
import com.example.momney.manager.R;
import com.example.momney.manager.data.MoneyDatabase;
import com.example.momney.manager.data.MoneyDatabaseImpl;
import com.example.momney.manager.data.MoneyEntry;
import com.example.momney.manager.screen.wallet.data.MoneyData;
import com.example.momney.manager.utils.Utils;
import com.google.gson.Gson;


import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class TransactionActivity extends AppCompatActivity implements Serializable {

    private int choseInc = 1;
    private static final String DATE_FORMAT = "EEE, MMM d, yyyy";

    Button mIncome;
    Button mExpense;
    View mView;
    TextView mDate;
    TextView mLabel;
    ImageButton mIcon;
    TextView mContent;
    EditText mAmount;
    EditText mNote;
    TextView mCurrency;

    long dateChose;
    int icon;
    boolean edit = false;

    MoneyEntry editMoney;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);

        mapView();
        setEditState();
        if (savedInstanceState != null) {
            mDate.setText(savedInstanceState.getString("date"));
                if(savedInstanceState.getBoolean("expense")) {
                choseInc = -1;
                mView.setBackground(ContextCompat.getDrawable(this, R.drawable.expense_background));
                mIncome.setTextColor(ContextCompat.getColor(this, R.color.opa_white));
                mExpense.setTextColor(ContextCompat.getColor(this, R.color.white));
            }
            if(savedInstanceState.getBoolean("content")) {
                mContent.setText(savedInstanceState.getString("content"));
                int icon = savedInstanceState.getInt("icon");
                Glide.with(this).load(icon).into(mIcon);
            }
        }

        setContent();

    }

    private void setEditState() {
        Gson gson = new Gson();
        MoneyData moneyData = gson.fromJson(getIntent().getStringExtra("myJson"), MoneyData.class);

        if(moneyData!=null){
            edit = true;
            editMoney = moneyData.getMoneyEntry();
            if(editMoney.getAmount()<0){
                mAmount.setText(String.valueOf(-(editMoney.getAmount())));
                mView.setBackground(ContextCompat.getDrawable(this, R.drawable.expense_background));
                choseInc = -1;
                mIncome.setTextColor(ContextCompat.getColor(this, R.color.opa_white));
                mExpense.setTextColor(ContextCompat.getColor(this, R.color.white));
            }
            else mAmount.setText(String.valueOf((editMoney.getAmount())));
            dateChose = editMoney.getTime();
            mDate.setText(getDateString(dateChose));
            mContent.setText(Utils.getContentFromInt(this, Integer.parseInt(editMoney.getContent())));
            mIcon.setImageResource(Utils.intToIcon(Integer.parseInt(editMoney.getContent())));
            mNote.setText(editMoney.getDescription());
            icon = Utils.intToIcon(Integer.parseInt(editMoney.getContent()));
        }

        if(edit){
            mLabel.setText(R.string.edit);
        }
    }

    private void mapView() {
        mView = findViewById(R.id.view);
        mAmount = findViewById(R.id.amount);
        mIncome = findViewById(R.id.income_button);
        mExpense = findViewById(R.id.year_button);
        mDate = findViewById(R.id.date);
        Calendar calendar = Calendar.getInstance();
        dateChose = calendar.getTimeInMillis();
        mDate.setText(getDateString(dateChose)) ;
        mIcon = findViewById(R.id.content_icon);
        mContent = findViewById(R.id.content);
        mNote = findViewById(R.id.add_note);
        mLabel = findViewById(R.id.label);
        mCurrency = findViewById(R.id.curency);
        mCurrency.setText(Utils.mCurrency);
    }

    private void setContent() {
        Intent intent = getIntent();
        String content = intent.getStringExtra(ContentActivity.EXTRA_CONTENT);
        if(content != null) {
            mContent.setText(content);
            icon = intent.getIntExtra(ContentActivity.EXTRA_ICON, 0);
            mIcon.setImageResource(icon);
        }
        Bundle state = intent.getBundleExtra("state");
        if(state != null) {
            dateChose = state.getLong("date");
            mDate.setText(getDateString(dateChose));
            if (state.getBoolean("expense")) {
                choseInc = -1;
                mView.setBackground(ContextCompat.getDrawable(this, R.drawable.expense_background));
                mIncome.setTextColor(ContextCompat.getColor(this, R.color.opa_white));
                mExpense.setTextColor(ContextCompat.getColor(this, R.color.white));
            }
            mAmount.setText(state.getString("amount"));
            mNote.setText(state.getString("note"));
        }
    }

    public String getDateString(long date) {
        Calendar thisDate = Calendar.getInstance();
        thisDate.setTimeInMillis(date);
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat format;
        Utils.setLocale(this, Utils.curLanguage.toLowerCase());
        if(thisDate.get(Calendar.DAY_OF_YEAR) == calendar.get(Calendar.DAY_OF_YEAR))
        {format = new SimpleDateFormat("MMM d, yyyy", Locale.getDefault());
        return this.getString(R.string.today) + ", " + format.format(date);}
        else  if(thisDate.get(Calendar.DAY_OF_YEAR) == calendar.get(Calendar.DAY_OF_YEAR)-1) {
            format = new SimpleDateFormat("MMM d, yyyy", Locale.getDefault());
            return this.getString(R.string.yesterday) + ", " + format.format(date);}
        else {
            format = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());
            return format.format(date);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if(choseInc != 1) {
            outState.putBoolean("expense", true);
        }
        outState.putString("date", mDate.getText().toString());
        if(mContent.getText() != null) {
            outState.putBoolean("content", true);
            outState.putString("content", mContent.getText().toString());
            outState.putInt("icon", mIcon.getImageAlpha());
        }

    }

    public void Return(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void ChoseIncome(View view) {
        choseInc = 1;
        mView.setBackground(ContextCompat.getDrawable(this, R.drawable.income_background));
        mIncome.setTextColor(ContextCompat.getColor(this, R.color.white));
        mExpense.setTextColor(ContextCompat.getColor(this, R.color.opa_white));
    }

    public void ChoseExpense(View view) {
        choseInc = -1;
        mView.setBackground(ContextCompat.getDrawable(this, R.drawable.expense_background));
        mIncome.setTextColor(ContextCompat.getColor(this, R.color.opa_white));
        mExpense.setTextColor(ContextCompat.getColor(this, R.color.white));
    }

    public void ChoseDate(View view) {
        DialogFragment newFragment = new ChoseDate();
        newFragment.show(getSupportFragmentManager(),"datePicker");
    }

    public void processDatePickerResult(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year,month,day);
        dateChose = calendar.getTimeInMillis();
        mDate.setText(getDateString(dateChose));


    }

    public void ChoseContent(View view) {
        Bundle outState = new Bundle();
        if(choseInc != 1) {
            outState.putBoolean("expense", true);
        }
        outState.putLong("date", dateChose);
        outState.putString("amount", mAmount.getText().toString());
        outState.putString("note", mNote.getText().toString());
        Intent intent = new Intent(TransactionActivity.this, ContentActivity.class);
        intent.putExtra("state", outState);
        startActivity(intent);
    }


    public void Submit(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        int i = Integer.parseInt(mAmount.getText().toString());
        MoneyDatabase db = new MoneyDatabaseImpl(this);

        if(edit){
            editMoney.setAmount(i*choseInc);
            editMoney.setTime(dateChose);
            editMoney.setContent(String.valueOf(Utils.iconToInt(icon)));
            editMoney.setDescription(mNote.getText().toString());
            db.update(editMoney);
        }
        else if(mAmount.getText().toString().length()!=0 && icon>0 ) {
            MoneyEntry money = new MoneyEntry(i*choseInc, dateChose,
                    String.valueOf(Utils.iconToInt(icon)), mNote.getText().toString());
            db.insert(money);
        }
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
