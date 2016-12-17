package com.study.online.utils;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by roy on 2016/12/16.
 */

public class StudyFragmentAdapter extends FragmentPagerAdapter {

    private List<Fragment> mFragmentList;
    private List<String> mList;

    public StudyFragmentAdapter(FragmentManager fm, List<Fragment> fragmentList, List<String> title) {
        super(fm);
        this.mFragmentList = fragmentList;
        this.mList = title;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mList.get(position % mList.size());
    }
}
