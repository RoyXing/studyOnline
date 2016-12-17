package com.design.studyonline.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.design.studyonline.R;
import com.design.studyonline.utils.StudyFragmentAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by roy on 2016/12/16.
 */

public class StudyFragment extends BaseFragment {

    private View mView;
    private TabLayout mLayout;
    private ViewPager mViewPager;
    private FragmentPagerAdapter mFragmentPagerAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_layout_study, null);
        initView(mView);
        initEvent();
        return mView;
    }

    private void initView(View mView) {
        mLayout = (TabLayout) mView.findViewById(R.id.tab_layout);
        mViewPager = (ViewPager) mView.findViewById(R.id.viewpager);
    }

    private void initEvent() {
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new CourseFragment());
        fragments.add(new EducationFragment());
        fragments.add(new ComputerFragment());
        List<String> titleList = new ArrayList<>();
        titleList.add("课程介绍");
        titleList.add("教育课程");
        titleList.add("计算机课程");
        mFragmentPagerAdapter = new StudyFragmentAdapter(getActivity().getSupportFragmentManager(), fragments, titleList);
        mViewPager.setAdapter(mFragmentPagerAdapter);
        mLayout.setupWithViewPager(mViewPager);
    }

}
