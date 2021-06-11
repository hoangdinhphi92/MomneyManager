package com.example.momney.manager.screen.wallet;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

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

import dev.shreyaspatil.MaterialDialog.AbstractDialog;
import dev.shreyaspatil.MaterialDialog.BottomSheetMaterialDialog;
import dev.shreyaspatil.MaterialDialog.interfaces.DialogInterface;

public class WalletFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    public final static int FILTER_WEEK = 2;
    public final static int FILTER_MONTH = 1;
    public final static int FILTER_ALL = 0;

    private MoneyDatabase database;
    TextView limit;
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
        limit = view.findViewById(R.id.limit);
        initRecycleView(view);
        initSpinner(view);

        database = new MoneyDatabaseImpl(getContext());
        Utils.getLimitText(limit, filterType, database);
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

                    BottomSheetMaterialDialog mBottomSheetDialog;
                    if(direction==4){
                        mBottomSheetDialog = new BottomSheetMaterialDialog.Builder(getActivity())
                                .setTitle(getString(R.string.delete) + "?")
                                .setMessage(getString(R.string.delete_alert))
                                .setCancelable(false)
                                .setAnimation(R.raw.delete_loader)
                                .setPositiveButton(getString(R.string.delete), R.drawable.ic_delete, new BottomSheetMaterialDialog.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int which) {
                                        database.delete(moneyData.getMoneyEntry());
                                        items.remove(moneyData);
                                        dialogInterface.dismiss();
                                        adapter.notifyItemRemoved(viewHolder.getAdapterPosition());
                                        updateData();
                                        if (!(Utils.weekUse == 0 || Utils.monthUse == 0 || Utils.yearUse == 0 ||
                                                Utils.limitOver(0, getContext()) || Utils.limitOver(1, getContext()) ||
                                                Utils.limitOver(2, getContext()))){
                                            MainActivity.alert.setVisibility(View.INVISIBLE);
                                            limit.setTextColor(getContext().getColor(R.color.income));
                                        }

                                    }
                                })
                                .setNegativeButton(getString(R.string.cancel), R.drawable.ic_cancel, new BottomSheetMaterialDialog.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int which) {
                                        dialogInterface.dismiss();
                                    }
                                })
                                .build();

                        // Show Dialog
                    }
                    else {
                        mBottomSheetDialog = new BottomSheetMaterialDialog.Builder(getActivity())
                                .setTitle(getString(R.string.edit) + "?")
                                .setMessage(getString(R.string.edit_alert))
                                .setCancelable(false)
                                .setAnimation(R.raw.edit_anim)
                                .setPositiveButton(getString(R.string.edit), R.drawable.ic_edit, new BottomSheetMaterialDialog.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int which) {
                                        Intent intent = new Intent(getActivity(), TransactionActivity.class);
                                        Gson gson = new Gson();
                                        String myJson = gson.toJson(moneyData);
                                        intent.putExtra("myJson", myJson);
                                        startActivity(intent);
                                    }
                                })
                                .setNegativeButton(getString(R.string.cancel), R.drawable.ic_cancel, new BottomSheetMaterialDialog.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int which) {
                                        dialogInterface.dismiss();
                                    }
                                })
                                .build();

                        // Show Dialog

                    }
                    mBottomSheetDialog.show();
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
        Utils.getLimitText(limit, filterType, database);
        updateData();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


}
