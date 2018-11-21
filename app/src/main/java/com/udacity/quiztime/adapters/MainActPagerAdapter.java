package com.udacity.quiztime.adapters;


import com.udacity.quiztime.ui.list.SavedFragment;
import com.udacity.quiztime.ui.list.TrendingFragment;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class MainActPagerAdapter extends FragmentStatePagerAdapter {
    private final int mNumOfTabs;

    public MainActPagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return new TrendingFragment();
            case 1:
                return new SavedFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}