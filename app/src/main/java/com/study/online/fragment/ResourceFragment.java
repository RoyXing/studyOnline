package com.study.online.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.study.online.R;
import com.study.online.utils.StudyFragmentAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by roy on 2016/12/16.
 */

public class ResourceFragment extends BaseFragment {

    private View mView;
    private TabLayout mLayout;
    private ViewPager mViewPager;
    private FragmentPagerAdapter mFragmentPagerAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_layout_resource, null);

        initView();
        initEvent();
        return mView;
    }

    private void initView() {
        mLayout = (TabLayout) mView.findViewById(R.id.resource_tab_layout);
        mViewPager = (ViewPager) mView.findViewById(R.id.resource_viewpager);
    }

    private void initEvent() {
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new ArticleFragment());
        fragments.add(new NetFragment());
        List<String> titleList = new ArrayList<>();
        titleList.add("论文期刊");
        titleList.add("学习网站");
        mFragmentPagerAdapter = new StudyFragmentAdapter(getActivity().getSupportFragmentManager(), fragments, titleList);
        mViewPager.setAdapter(mFragmentPagerAdapter);
        mLayout.setupWithViewPager(mViewPager);
    }

}
