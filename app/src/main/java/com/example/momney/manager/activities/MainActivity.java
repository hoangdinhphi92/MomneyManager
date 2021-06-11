package com.example.momney.manager.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.momney.manager.R;
import com.example.momney.manager.data.MoneyDatabase;
import com.example.momney.manager.screen.MyPagerAdapter;
import com.example.momney.manager.screen.wallet.WalletFragment;
import com.example.momney.manager.utils.Utils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import org.jetbrains.annotations.NotNull;

import dev.shreyaspatil.MaterialDialog.AbstractDialog;
import dev.shreyaspatil.MaterialDialog.BottomSheetMaterialDialog;
import dev.shreyaspatil.MaterialDialog.interfaces.DialogInterface;

public class MainActivity extends AppCompatActivity {
    private MoneyDatabase database;
    Bundle bundle;
    public static ImageView alert;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Utils.restoreState(this);
        Utils.restore();
        initFloatingButton();

        TabLayout tabLayout = initTab();

        TextView textView = findViewById(R.id.tab_name);
        alert = findViewById(R.id.alert_icon);
        if(Utils.weekUse == 0 || Utils.monthUse == 0 || Utils.yearUse == 0 ||
                Utils.limitOver(0, this) || Utils.limitOver(1, this) ||
                Utils.limitOver(2, this)){
            alert.setVisibility(View.VISIBLE);
        }
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
        }
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
                startActivity(intent);

            }
        });
    }



    public MoneyDatabase getDatabase() {
        return database;
    }

    public void Setting(View view) {
        Intent intent = new Intent(MainActivity.this, SettingActivity.class);
        startActivity(intent);
    }

    public void Alert(View view) {
        if(Utils.weekUse == 0 || Utils.monthUse == 0 || Utils.yearUse == 0  ||
                Utils.limitOver(0, this) || Utils.limitOver(1, this) ||
                Utils.limitOver(2, this)){
            BottomSheetMaterialDialog mBottomSheetDialog = new BottomSheetMaterialDialog.Builder(this)
                    .setTitle(getString(R.string.notifi))
                    .setMessage( getString(R.string.notify_desc) )
                    .setCancelable(false)
                    .setAnimation(R.raw.notification_bell)
                    .setPositiveButton(getString(R.string.confirm), R.drawable.ic_confirm, new BottomSheetMaterialDialog.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int which) {
                            Intent intent = new Intent(MainActivity.this, SettingActivity.class);
                            startActivity(intent);
                        }
                    })
                    .setNegativeButton(getString(R.string.later), R.drawable.ic_cancel, new BottomSheetMaterialDialog.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int which) {
                            dialogInterface.dismiss();
                        }
                    })
                    .build();

            // Show Dialog
            mBottomSheetDialog.show();
        }
    }
}