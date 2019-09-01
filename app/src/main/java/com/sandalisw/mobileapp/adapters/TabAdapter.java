package com.sandalisw.mobileapp.adapters;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.sandalisw.mobileapp.R;
import com.sandalisw.mobileapp.ui.MediaControllerFragment;
import com.sandalisw.mobileapp.ui.PlayerFragment;

import java.util.ArrayList;
import java.util.List;

public class TabAdapter extends FragmentPagerAdapter {

    private final List<Fragment> listFragment = new ArrayList<>();
    private final List<String> listTitles = new ArrayList<>();
    private Context context;

    public TabAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        return listFragment.get(i);
    }

    @Override
    public int getCount() {
        return listTitles.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int i) {
        return listTitles.get(i);
    }

    public void addFragment(Fragment fragment, String title){
        listFragment.add(fragment);
        listTitles.add(title);
    }



}
