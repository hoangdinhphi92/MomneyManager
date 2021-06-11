package com.example.momney.manager.screen.wallet;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.momney.manager.R;
import com.example.momney.manager.activities.MainActivity;
import com.example.momney.manager.activities.TransactionActivity;
import com.example.momney.manager.data.MoneyDatabase;
import com.example.momney.manager.data.MoneyDatabaseImpl;
import com.example.momney.manager.data.MoneyEntry;
import com.example.momney.manager.screen.wallet.data.DateHeader;
import com.example.momney.manager.screen.wallet.data.MoneyData;
import com.example.momney.manager.screen.wallet.data.TotalHeader;
import com.example.momney.manager.screen.wallet.data.TransactionData;
import com.example.momney.manager.screen.wallet.viewholder.MoneyViewHolder;
import com.example.momney.manager.utils.Utils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Random;

public class WalletFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    public final static int FILTER_WEEK = 2;
    public final static int FILTER_MONTH = 1;
    public final static int FILTER_ALL = 0;

    private MoneyDatabase database;

    private RecyclerView recyclerView;
    private TransactionAdapter adapter;
    private int filterType = FILTER_ALL;
    ArrayList<TransactionData> items;

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

        database = new MoneyDatabaseImpl(getContext());

        updateData();

        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(RecyclerView recyclerView,
                                  RecyclerView.ViewHolder viewHolder,
                                  RecyclerView.ViewHolder target) {
                return false;
            }


            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder,
                                 int direction) {
                if (viewHolder instanceof MoneyViewHolder) {
                    MoneyData moneyData = ((MoneyViewHolder) viewHolder).getMoneyData();

                    if(direction==4){
                        database.delete(moneyData.getMoneyEntry());
                        items.remove(moneyData);

                        adapter.notifyItemRemoved(viewHolder.getAdapterPosition());
                    }
                    else {
                        Intent intent = new Intent(getActivity(), TransactionActivity.class);
                        Gson gson = new Gson();
                        String myJson = gson.toJson(moneyData);
                        intent.putExtra("myJson", myJson);
                        startActivity(intent);
                    }
                    updateData();
                }

            }

        });
        helper.attachToRecyclerView(recyclerView);

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

        items = new ArrayList<>();

        // Create total data
        TotalHeader totalHeader = new TotalHeader(database.getAllIncome(), database.getAllExpense());
        items.add(totalHeader);
        // Group money entry with header
        if (database.getAllTransactions().size() != 0) {
            ArrayList<Long> date = new ArrayList<>();
            ArrayList<MoneyEntry> moneyEntries = (ArrayList<MoneyEntry>) database.getAllTransactions();
            for (MoneyEntry moneyEntry : moneyEntries) {
                date.add(moneyEntry.getTime());
            }

            DateHeader dateHeader1 = new DateHeader(date.get(0), database.total(date.get(0), filterType), filterType);
            items.add(dateHeader1);
            MoneyData moneyData1 = new MoneyData(moneyEntries.get(0));
            items.add(moneyData1);

            for (int i = 1; i < date.size(); i++) {
                if (Utils.differentTime(date.get(i - 1), date.get(i), filterType)) {
                    DateHeader dateHeader = new DateHeader(date.get(i), database.total(date.get(i), filterType), filterType);
                    items.add(dateHeader);
                    MoneyData moneyData = new MoneyData(moneyEntries.get(i));
                    items.add(moneyData);
                } else {
                    MoneyData moneyData = new MoneyData(moneyEntries.get(i));
                    items.add(moneyData);
                }
            }
        }
        // Today


        adapter.submitList(items);
    }

    private void addTest() {
        Random random = new Random();
        int contentId = random.nextInt(15);
        String content = String.valueOf(contentId);
        int amount = (10 + random.nextInt(900)) * 1000;
        long time = System.currentTimeMillis() - (random.nextInt(30) * 24L * 60L * 60L * 1000L);

        MoneyEntry entry = new MoneyEntry(amount, time, content, content);
        database.insert(entry);
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
