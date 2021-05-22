package com.example.momney.manager.screen.wallet;

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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.momney.manager.R;
import com.example.momney.manager.activities.MainActivity;
import com.example.momney.manager.activities.TransactionActivity;
import com.example.momney.manager.data.MoneyDatabase;
import com.example.momney.manager.data.MoneyDatabaseImpl;
import com.example.momney.manager.data.MoneyEntry;
import com.example.momney.manager.data.Transaction;
import com.example.momney.manager.screen.wallet.data.DateHeader;
import com.example.momney.manager.screen.wallet.data.MoneyData;
import com.example.momney.manager.screen.wallet.data.TransactionData;

import java.util.ArrayList;
import java.util.List;

public class WalletFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    public final static int FILTER_WEEK = 2;
    public final static int FILTER_MONTH = 1;
    public final static int FILTER_ALL = 0;

    private MoneyDatabase database;

    private RecyclerView recyclerView;
    private TransactionAdapter adapter;
    private int filterType = FILTER_ALL;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_wallet, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initRecycleView(view);
        initSpinner(view);
        updateData();
    }

    private void initRecycleView(View view) {
        recyclerView = view.findViewById(R.id.transaction);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        adapter = new TransactionAdapter();
        recyclerView.setAdapter(adapter);
    }

    private void initSpinner(View v) {
        Spinner spinner = v.findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                requireActivity(),
                R.array.time_array,
                android.R.layout.simple_spinner_item
        );

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
    }

    private void updateData() {
        ArrayList<TransactionData> items = new ArrayList<>();
        // Create total data


        // Group money entry with header

        // Today


        adapter.submitList(items);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        filterType = position;
        updateData();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
