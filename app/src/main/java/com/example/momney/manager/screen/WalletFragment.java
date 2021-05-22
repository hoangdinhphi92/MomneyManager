package com.example.momney.manager.screen;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.momney.manager.R;
import com.example.momney.manager.activities.MainActivity;
import com.example.momney.manager.activities.TransactionActivity;
import com.example.momney.manager.data.Content;
import com.example.momney.manager.data.MoneyDatabase;
import com.example.momney.manager.data.MoneyDatabaseImpl;
import com.example.momney.manager.data.MoneyEntry;
import com.example.momney.manager.data.Transaction;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Objects;

public class WalletFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private MoneyDatabase database;
    private MainActivity mainActivity;
    private ArrayList<Transaction> mTrans;
    private TransactionAdapter mAdapter;
    TextView mIncome;
    TextView mExpense;
    public WalletFragment() {
    }

    @SuppressLint("SetTextI18n")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_wallet, container, false);

        mIncome = v.findViewById(R.id.total_income) ;
        mExpense = v.findViewById(R.id.total_expense);
        mainActivity = (MainActivity) getActivity();

//        if(mainActivity.getBundle()!= null) {
//            Toast m = Toast.makeText(v.getContext(), mainActivity.getBundle().getInt("new_date"), Toast.LENGTH_SHORT);
//            m.show();
//        }
        initSpiner(v);


        database = new MoneyDatabaseImpl(v.getContext());
//        if (getArguments() != null) {
//            Bundle bundle = getArguments();
//            int newAmount = bundle.getInt("new_amount");
//            int date = bundle.getInt("new_date");
//            Toast m = Toast.makeText(v.getContext(), newAmount, Toast.LENGTH_SHORT);
//            m.show();
//        }
//        database.deleteTable();
//        String[] contentList = getResources()
//                .getStringArray(R.array.content);
//
//        for(int i=0; i<contentList.length; i++) {
//            int n = (i%2 == 1) ? 1 :-1;
//            MoneyEntry a = new MoneyEntry(i+1, i*n*5000 , "15-"+String.valueOf(i)+"-2000", contentList[i], "T7");
//            database.insert(a);
//        }


        mIncome.setText(database.getAllIncome()+" VND");
        mExpense.setText(database.getAllExpense()+" VND");

        RecyclerView mRecyclerView = v.findViewById(R.id.transaction);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(v.getContext()));
        mTrans = new ArrayList<>();
        mAdapter = new TransactionAdapter(mTrans, v.getContext());
        mRecyclerView.setAdapter(mAdapter);
        initializeData();

        return v;

    }

    private void initSpiner(View v) {
        Spinner spinner =(Spinner) v.findViewById(R.id.spinner1);
        if (spinner != null) {
            spinner.setOnItemSelectedListener(this);
        }
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getActivity(),
                R.array.time_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource
                (android.R.layout.simple_spinner_dropdown_item);
        if (spinner != null) {
            spinner.setAdapter(adapter);
        }
    }

    private void initializeData() {
        ArrayList<MoneyEntry> moneyEntries = (ArrayList<MoneyEntry>) database.getAllTransactions();
        ArrayList<String> content = new ArrayList<>();
        ArrayList<String> description = new ArrayList<>();
        ArrayList<Integer> amount = new ArrayList<>();
        ArrayList<Integer> icon = new ArrayList<>();
        database.delete(moneyEntries.get(moneyEntries.size()-1));
        for (MoneyEntry moneyEntry: moneyEntries){
            content.add(moneyEntry.getContent());
            description.add(moneyEntry.getDescription());
            amount.add(moneyEntry.getAmount());
            icon.add(getIcon(moneyEntry.getContent()));
        }
        mTrans.clear();
        for (int i = 0; i < content.size(); i++) {
            mTrans.add(new Transaction(content.get(i), description.get(i), amount.get(i), icon.get(i)));
        }
        mAdapter.notifyDataSetChanged();
    }

    private Integer getIcon(String content) {
        switch (content) {
            case "Eating":
                return (int) R.drawable.img_eating;
            case "Travel":
                return (int) R.drawable.img_travel;
            case "Sport":
                return (int) R.drawable.img_sport;
            case "House rent":
                return (int) R.drawable.img_house;
            case "Bill":
                return (int) R.drawable.img_bill;
            case "Health":
                return (int) R.drawable.img_health;
            case "Education":
                return (int) R.drawable.img_education;
            case "Game":
                return (int) R.drawable.img_game;
            case "Online Services":
                return (int) R.drawable.img_services;
            case "Salary":
                return (int) R.drawable.img_salary;
            case "Houseware":
                return (int) R.drawable.img_houseware;
            case "Transfers":
                return (int) R.drawable.img_transfer;
            case "Pet":
                return (int) R.drawable.img_pet;
            case "Vehicle":
                return (int) R.drawable.img_vehicle;
            case "Others":
                return (int) R.drawable.img_other;
            default:
                return 0;
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof MainActivity) {
            database = ((MainActivity) context).getDatabase();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public boolean onContextItemSelected(@NonNull @NotNull MenuItem item) {
        switch (item.getItemId()){
            case 12:
                edit(item.getGroupId());
                return true;
            case 13:
                delete(item.getGroupId());
                return true;
            default:
                return super.onContextItemSelected(item);
        }

    }

    private void delete(int groupId) {
        ArrayList<MoneyEntry> moneyEntries = (ArrayList<MoneyEntry>) database.getAllTransactions();
        AlertDialog.Builder myAlertBuilder = new
                AlertDialog.Builder(getActivity());
        myAlertBuilder.setTitle("Alert");
        myAlertBuilder.setMessage("Do you want to delete this transaction?");
        myAlertBuilder.setPositiveButton("OK", new
                DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        mAdapter.notifyItemRemoved(groupId);
                        database.delete(moneyEntries.get(groupId));
                    }
                });
        myAlertBuilder.setNegativeButton("Cancel", new
                DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        myAlertBuilder.show();

    }

    private void edit(int groupId) {
        ArrayList<MoneyEntry> moneyEntries = (ArrayList<MoneyEntry>) database.getAllTransactions();
        Intent intent = new Intent(getActivity(), TransactionActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("cur_amount", moneyEntries.get(groupId).getAmount());


    }
}
