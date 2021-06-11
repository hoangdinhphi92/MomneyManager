package com.example.momney.manager.screen;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.momney.manager.R;
import com.example.momney.manager.activities.MainActivity;
import com.example.momney.manager.data.MoneyDatabase;
import com.example.momney.manager.data.MoneyDatabaseImpl;
import com.example.momney.manager.utils.Utils;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.whiteelephant.monthpicker.MonthPickerDialog;
import com.wx.wheelview.widget.WheelViewDialog;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Calendar;

import static com.example.momney.manager.screen.wallet.WalletFragment.FILTER_ALL;
import static java.lang.Math.abs;
import static java.lang.Math.scalb;
import static java.lang.Math.sqrt;

public class ReportFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    public final static int FILTER_WEEK = 2;
    public final static int FILTER_MONTH = 1;
    public final static int FILTER_ALL = 0;

    private MoneyDatabase database;
    LineChart lineChart;
    PieChart pieChart;
    TextView totalIncome, totalExpense, label;
    ImageView mSwitch, mChoseTime;

    private int filterType = FILTER_ALL;

    private long date;
    private int monthChose;
    boolean isExpense = false;

    Calendar calendar;
    public ReportFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_report, container, false);
        database = new MoneyDatabaseImpl(getContext());
        lineChart = (LineChart) v.findViewById(R.id.line_chart);
        pieChart = v.findViewById(R.id.pie_chart);
        mSwitch = v.findViewById(R.id.switch_income);
        mChoseTime = v.findViewById(R.id.calendar);
        totalIncome = v.findViewById(R.id.total_income);
        totalExpense = v.findViewById(R.id.total_expense);
        label = v.findViewById(R.id.time);
        calendar = Calendar.getInstance();
        date = calendar.getTimeInMillis();

        initSpinner(v);

        return v;

    }


    private void initSpinner(View v) {
        Spinner spinner = (Spinner) v.findViewById(R.id.spinner1);
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

    private void initLineChart() {
        XAxis xAxis = lineChart.getXAxis();
        lineChart.fitScreen();
        if(lineChart.getData() != null)
             lineChart.getData().clearValues();
        lineChart.notifyDataSetChanged();
        lineChart.clear();
        lineChart.invalidate();
        lineChart.setDragEnabled(true);

        lineChart.setScaleEnabled(true);
        //lineChart.animate();
        ArrayList<Entry> valueIncome = Utils.incomeEntry(filterType, date, database);
        ArrayList<Entry> valueExpense = Utils.expenseEntry(filterType, date, database);


        LineDataSet set = new LineDataSet(valueIncome, getString(R.string.income));
        set.setFillAlpha(200);
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        LineDataSet set1 = new LineDataSet(valueExpense, getString(R.string.expense));
        set1.setFillAlpha(200);
        set1.setColor(Color.RED);
        dataSets.add(set);
        dataSets.add(set1);
        ArrayList<String> values = Utils.getXAxis(filterType, date, getContext());


        xAxis.setValueFormatter(new MyXAxisValueFormatter(values));
//        xAxis.setGranularity(1f);

        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        LineData data = new LineData(dataSets);

        lineChart.setData(data);
//        lineChart.invalidate();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isExpense = !isExpense;
                setupPieChart(isExpense);
                if (isExpense) {
                    loadPieChartDataExpense();
                } else loadPieChartDataIncome();
            }
        });

        update();

        choseTime();


    }

    private void choseTime() {

        mChoseTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar today = Calendar.getInstance();
                today.setTimeInMillis(date);
                MonthPickerDialog.Builder builder = new MonthPickerDialog.Builder(getContext(),
                        new MonthPickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(int selectedMonth, int selectedYear) {
                                date = Utils.getMonthInMillis(selectedYear, selectedMonth);
                                if(filterType==FILTER_ALL){
                                    label.setText(String.valueOf(selectedYear));
                                }
                                else
                                    label.setText(Utils.millisecondToString(date, filterType, getContext()));
                                update();

                            }
                        }, today.get(Calendar.YEAR), today.get(Calendar.MONTH));
                switch (filterType) {
                    case FILTER_ALL:
                        builder.setActivatedMonth(today.get(Calendar.MONTH))
                                .setMinYear(1990)
                                .setActivatedYear(today.get(Calendar.YEAR))
                                .setMaxYear(2050)
                                .setMinMonth(Calendar.JANUARY)
                                .setTitle(getString(R.string.select_year))
                                .setMonthRange(Calendar.JANUARY, Calendar.DECEMBER)
                                .showYearOnly()
                                .setOnYearChangedListener(new MonthPickerDialog.OnYearChangedListener() {
                                    @Override
                                    public void onYearChanged(int year) {
//                                        date = Utils.getYearInMillis(year);
//                                        update();
                                    }
                                })
                                .build().show();
                        break;

                    case FILTER_MONTH:
                        builder.setActivatedMonth(today.get(Calendar.MONTH))
                                .setMinYear(1990)
                                .setActivatedYear(today.get(Calendar.YEAR))
                                .setMaxYear(2050)
                                .setMinMonth(Calendar.JANUARY)
                                .setTitle(getString(R.string.select_month))
                                .setMonthRange(Calendar.JANUARY, Calendar.DECEMBER)
                                .setOnMonthChangedListener(new MonthPickerDialog.OnMonthChangedListener() {
                                    @Override
                                    public void onMonthChanged(int selectedMonth) {
                                        monthChose = selectedMonth;
                                        date = Utils.getMonthInMillis(today.get(Calendar.YEAR), monthChose);
                                        update();
                                    }
                                })
                                .setOnYearChangedListener(new MonthPickerDialog.OnYearChangedListener() {
                                    @Override
                                    public void onYearChanged(int year) {
                                        date = Utils.getMonthInMillis(year, monthChose);
                                        update();
                                    }
                                })
                                .build().show();
                        break;
                    case FILTER_WEEK:
                        show_wheelView();
                        break;
                }
            }
        });

    }

    private void update() {
        setupPieChart(isExpense);
        if (isExpense) {
            loadPieChartDataExpense();
        } else loadPieChartDataIncome();

        totalExpense.setText(Utils.amountToString((int) database.totalUse(date, filterType)));
        totalIncome.setText(Utils.amountToString((int) database.totalIncome(date, filterType)));

        initLineChart();

    }


    private void setupPieChart(boolean isExpense) {
        pieChart.setDrawHoleEnabled(true);
        pieChart.setUsePercentValues(true);
        pieChart.setEntryLabelTextSize(12);
        pieChart.setEntryLabelColor(Color.BLACK);
        pieChart.setEntryLabelTypeface(Typeface.SERIF);
        if (isExpense)
            pieChart.setCenterText(getString(R.string.expense_center));
        else pieChart.setCenterText(getString(R.string.income_center));
        pieChart.setCenterTextSize(24);
        pieChart.setDrawEntryLabels(true);
        pieChart.getDescription().setEnabled(false);

        Legend l = pieChart.getLegend();
        l.setEnabled(false);
    }

    private void loadPieChartDataExpense() {
        ArrayList<PieEntry> entries = new ArrayList<>();
        if (database.totalUseContent(date, filterType, "1") > 0)
            entries.add(new PieEntry(database.totalUseContent(date, FILTER_ALL, "1"),
                    getString(R.string.eating)));
        if (database.totalUseContent(date, filterType, "2") > 0)
            entries.add(new PieEntry(database.totalUseContent(date, FILTER_ALL, "2"),
                    getString(R.string.travel)));
        if (database.totalUseContent(date, filterType, "3") > 0)
            entries.add(new PieEntry(database.totalUseContent(date, FILTER_ALL, "3"),
                    getString(R.string.sport)));
        if (database.totalUseContent(date, filterType, "4") > 0)
            entries.add(new PieEntry(database.totalUseContent(date, FILTER_ALL, "4"),
                    getString(R.string.house_rent)));
        if (database.totalUseContent(date, filterType, "5") > 0)
            entries.add(new PieEntry(database.totalUseContent(date, FILTER_ALL, "5"),
                    getString(R.string.bill)));
        if (database.totalUseContent(date, filterType, "6") > 0)
            entries.add(new PieEntry(database.totalUseContent(date, FILTER_ALL, "6"),
                    getString(R.string.health)));
        if (database.totalUseContent(date, filterType, "7") > 0)
            entries.add(new PieEntry(database.totalUseContent(date, FILTER_ALL, "7"),
                    getString(R.string.education)));
        if (database.totalUseContent(date, filterType, "8") > 0)
            entries.add(new PieEntry(database.totalUseContent(date, FILTER_ALL, "8"),
                    getString(R.string.game)));
        if (database.totalUseContent(date, filterType, "9") > 0)
            entries.add(new PieEntry(database.totalUseContent(date, FILTER_ALL, "9"),
                    getString(R.string.online_services)));
        if (database.totalUseContent(date, filterType, "11") > 0)
            entries.add(new PieEntry(database.totalUseContent(date, FILTER_ALL, "11"),
                    getString(R.string.house_rent)));
        if (database.totalUseContent(date, filterType, "12") > 0)
            entries.add(new PieEntry(database.totalUseContent(date, FILTER_ALL, "12"),
                    getString(R.string.transfers)));
        if (database.totalUseContent(date, filterType, "13") > 0)
            entries.add(new PieEntry(database.totalUseContent(date, FILTER_ALL, "13"),
                    getString(R.string.pet)));
        if (database.totalUseContent(date, filterType, "14") > 0)
            entries.add(new PieEntry(database.totalUseContent(date, FILTER_ALL, "14"),
                    getString(R.string.vihicle)));
        if (database.totalUseContent(date, filterType, "15") > 0)
            entries.add(new PieEntry(database.totalUseContent(date, FILTER_ALL, "15"),
                    getString(R.string.orthers)));


        ArrayList<Integer> colors = new ArrayList<>();
        for (int color : ColorTemplate.MATERIAL_COLORS) {
            colors.add(color);
        }

        for (int color : ColorTemplate.VORDIPLOM_COLORS) {
            colors.add(color);
        }

        PieDataSet dataSet = new PieDataSet(entries, "Expense");
        dataSet.setColors(colors);

        PieData data = new PieData(dataSet);
        data.setDrawValues(true);
        data.setValueFormatter(new PercentFormatter(pieChart));
        data.setValueTextSize(12f);
        data.setValueTextColor(Color.BLACK);

        pieChart.setData(data);
        pieChart.invalidate();

        pieChart.animateY(1500, Easing.EaseInCubic);
    }

    private void loadPieChartDataIncome() {
        ArrayList<PieEntry> entries = new ArrayList<>();
        if (database.totalIncomeContent(date, filterType, "3") > 0)
            entries.add(new PieEntry(database.totalIncomeContent(date, FILTER_ALL, "3"),
                    getString(R.string.sport)));
        if (database.totalIncomeContent(date, filterType, "7") > 0)
            entries.add(new PieEntry(database.totalIncomeContent(date, FILTER_ALL, "7"),
                    getString(R.string.education)));
        if (database.totalIncomeContent(date, filterType, "8") > 0)
            entries.add(new PieEntry(database.totalIncomeContent(date, FILTER_ALL, "8"),
                    getString(R.string.game)));
        if (database.totalIncomeContent(date, filterType, "10") > 0)
            entries.add(new PieEntry(database.totalIncomeContent(date, FILTER_ALL, "10"),
                    getString(R.string.salary)));
        if (database.totalIncomeContent(date, filterType, "12") > 0)
            entries.add(new PieEntry(database.totalIncomeContent(date, FILTER_ALL, "12"),
                    getString(R.string.transfers)));
        if (database.totalIncomeContent(date, filterType, "9") > 0)
            entries.add(new PieEntry(database.totalIncomeContent(date, FILTER_ALL, "9"),
                    getString(R.string.online_services)));
        if (database.totalIncomeContent(date, filterType, "15") > 0)
            entries.add(new PieEntry(database.totalIncomeContent(date, FILTER_ALL, "15"),
                    getString(R.string.orthers)));

        ArrayList<Integer> colors = new ArrayList<>();
        for (int color : ColorTemplate.MATERIAL_COLORS) {
            colors.add(color);
        }

        for (int color : ColorTemplate.VORDIPLOM_COLORS) {
            colors.add(color);
        }

        PieDataSet dataSet = new PieDataSet(entries, "Income");
        dataSet.setColors(colors);

        PieData data = new PieData(dataSet);
        data.setDrawValues(true);
        data.setValueFormatter(new PercentFormatter(pieChart));
        data.setValueTextSize(12f);
        data.setValueTextColor(Color.BLACK);

        pieChart.setData(data);
        pieChart.invalidate();

        pieChart.animateY(1500, Easing.EaseInCubic);
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
        filterType = position;
        date = calendar.getTimeInMillis();
        switch (position){
            case FILTER_ALL:
                label.setText(R.string.this_year);
                break;
            case FILTER_MONTH:
                label.setText(R.string.this_month);
                break;
            case FILTER_WEEK:
                label.setText(R.string.this_week);
                break;
        }
        update();
    }


    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void show_wheelView()
    {
        date = calendar.getTimeInMillis();
        ArrayList<Long> dateList = Utils.wheelList(date);
        ArrayList<String> wheelList = new ArrayList<>();
        for(int i=0; i< dateList.size(); i++){
            wheelList.add(Utils.getStringWeek(dateList.get(i)));
        }
        WheelViewDialog dialog = new WheelViewDialog(getContext());
        dialog.setTitle(getString(R.string.chose_week))
                .setItems(wheelList)
                .setDialogStyle(Color.parseColor("#6699ff"))
                .setCount(3)
                .setLoop(false)
                .setButtonText("Ok")
                .setOnDialogItemClickListener(new WheelViewDialog.OnDialogItemClickListener() {
                    @Override
                    public void onItemClick(int position, String s) {
                        date = dateList.get(position);
                        label.setText(s);
                        update();
                    }
                })
                .show();
    }

    public static class MyXAxisValueFormatter extends ValueFormatter {

        public ArrayList<String> values;

        public MyXAxisValueFormatter(ArrayList<String> values) {
            this.values = values;
        }

        @Override
        public String getFormattedValue(float value) {
            return values.get((int) value);
        }
    }
}
