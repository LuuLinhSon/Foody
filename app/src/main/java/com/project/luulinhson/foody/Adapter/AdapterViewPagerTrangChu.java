package com.project.luulinhson.foody.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.project.luulinhson.foody.View.Main.Fragment.FragmentAnGi;
import com.project.luulinhson.foody.View.Main.Fragment.FragmentODau;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 7/20/2017.
 */

public class AdapterViewPagerTrangChu extends FragmentPagerAdapter {

    List<Fragment> fragmentList = new ArrayList<>();

    public AdapterViewPagerTrangChu(FragmentManager fm) {
        super(fm);
        fragmentList.add(new FragmentODau());
        fragmentList.add(new FragmentAnGi());
    }

    @Override
    public Fragment getItem(int i) {
        return fragmentList.get(i);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }
}
