package com.example.momney.manager.screen;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.momney.manager.screen.wallet.WalletFragment;

public class MyPagerAdapter extends FragmentStatePagerAdapter {

    int mNumOfTabs;


    public MyPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm);
        this.mNumOfTabs = behavior;
    }


    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new WalletFragment();
            case 1:
                return new ReportFragment();
            default:
                return null;
        }
    }

    /**
     * Return the number of views available.
     */
    @Override
    public int getCount() {
        return mNumOfTabs;
    }


}
