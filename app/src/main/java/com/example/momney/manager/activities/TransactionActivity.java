package com.example.momney.manager.activities;

import android.annotation.SuppressLint;
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


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class TransactionActivity extends AppCompatActivity {

    private int choseInc = 1;

    private static final String DATE_FORMAT = "EEE, MMM d, yyyy";

    Button mIncome;
    Button mExpense;
    View mView;
    TextView mDate;
    ImageButton mIcon;
    TextView mContent;
    EditText mAmount;
    EditText mNote;

    long dateChose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);
        mapView();

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
    }

    private void setContent() {
        Intent intent = getIntent();
        String content = intent.getStringExtra(ContentActivity.EXTRA_CONTENT);
        if(content != null) {
            mContent.setText(content);
            int icon = intent.getIntExtra(ContentActivity.EXTRA_ICON, 0);
            Glide.with(this).load(icon).into(mIcon);
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

    public static String getDateString(long date) {
        Calendar thisDate = Calendar.getInstance();
        thisDate.setTimeInMillis(date);
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat format;
        if(thisDate.get(Calendar.DAY_OF_YEAR) == calendar.get(Calendar.DAY_OF_YEAR))
        {format = new SimpleDateFormat("MMM d, yyyy", Locale.US);
        return "Today, " + format.format(date);}
        else  if(thisDate.get(Calendar.DAY_OF_YEAR) == calendar.get(Calendar.DAY_OF_YEAR)-1) {
            format = new SimpleDateFormat("MMM d, yyyy", Locale.US);
            return "Yesterday, " + format.format(date);}
        else {
            format = new SimpleDateFormat(DATE_FORMAT, Locale.US);
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

        if(mAmount.getText().toString().length()!=0 ) {
            Intent intent = new Intent(this, MainActivity.class);
            int i = Integer.parseInt(mAmount.getText().toString());
            MoneyDatabase db = new MoneyDatabaseImpl(this);
            MoneyEntry money = new MoneyEntry(i*choseInc, dateChose, mContent.getText().toString(), mNote.getText().toString());
            db.insert(money);
            startActivity(intent);
//            Bundle bundle = new Bundle();
//            bundle.putInt("new_amount", i * choseInc);
//            bundle.putInt("new_date", dateChose);
//            bundle.putString("new_content", mContent.getText().toString());
//            bundle.putInt("new_icon", mIcon.getImageAlpha());
//            if(mNote.getText().toString().length()!=0){
//                bundle.putBoolean("new_desc", true);
//                bundle.putString("new_desc", mNote.getText().toString());
        }




    }
}
