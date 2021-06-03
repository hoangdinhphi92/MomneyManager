package com.example.momney.manager.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.momney.manager.R;
import com.example.momney.manager.SettingActivity;
import com.example.momney.manager.data.MoneyDatabase;
import com.example.momney.manager.screen.MyPagerAdapter;
import com.example.momney.manager.screen.wallet.WalletFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import org.jetbrains.annotations.NotNull;

public class MainActivity extends AppCompatActivity {
    private MoneyDatabase database;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initFloatingButton();

        TabLayout tabLayout = initTab();

        TextView textView = findViewById(R.id.tab_name);
        textView.setText(R.string.wallet);

        initViewPager(tabLayout, textView);

        Intent intent = getIntent();
        bundle = intent.getBundleExtra("newMoney");
        if (bundle != null) {
            FragmentManager manager = getSupportFragmentManager();
            final FragmentTransaction transaction=manager.beginTransaction();
            WalletFragment newTrans = new WalletFragment();
            newTrans.setArguments(bundle);
            transaction.replace(R.id.view_pager, newTrans);
            transaction.commit();
//            showToast(bundle.getString("new_content"));
//            showToast(String.valueOf(bundle.getInt("new_amount")));
//            showToast(String.valueOf(bundle.getInt("new_date")));
//            if (bundle.getBoolean("new_desc"))
//                showToast(bundle.getString("new_desc"));
        }
    }

    public Bundle getBundle() {
        return bundle;
    }

    private void showToast(String new_content) {
        Toast m = Toast.makeText(this, new_content, Toast.LENGTH_SHORT);
        m.show();
    }


    private void initViewPager(TabLayout tabLayout, TextView textView) {
        final ViewPager viewPager = findViewById(R.id.view_pager);
        final PagerAdapter adapter = new MyPagerAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new
                TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new
                                                   TabLayout.OnTabSelectedListener() {
                                                       @Override
                                                       public void onTabSelected(TabLayout.Tab tab) {
                                                           viewPager.setCurrentItem(tab.getPosition());
                                                           if(tab.getPosition() == 0) { textView.setText(R.string.wallet); }
                                                           else textView.setText(R.string.report);
                                                       }

                                                       @Override
                                                       public void onTabUnselected(TabLayout.Tab tab) {
                                                       }

                                                       @Override
                                                       public void onTabReselected(TabLayout.Tab tab) {
                                                       }
                                                   });
    }

    @NotNull
    private TabLayout initTab() {
        TabLayout tabLayout = findViewById(R.id.tabLayout);
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_wallet));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_chart));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        return tabLayout;
    }

    private void initFloatingButton() {
        FloatingActionButton fab = findViewById(R.id.add_trans);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, TransactionActivity.class);
                startActivityForResult(intent, 125);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 125 && resultCode == Activity.RESULT_OK) {
            database.insert();
        }
    }

    public MoneyDatabase getDatabase() {
        return database;
    }

    public void Setting(View view) {
        Intent intent = new Intent(MainActivity.this, SettingActivity.class);
        startActivity(intent);
    }
}