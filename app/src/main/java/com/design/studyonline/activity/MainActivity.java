package com.design.studyonline.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.design.studyonline.R;
import com.design.studyonline.bean.BottomBean;
import com.design.studyonline.data.BottomData;
import com.design.studyonline.fragment.CommunicationFragment;
import com.design.studyonline.fragment.ResourceFragment;
import com.design.studyonline.fragment.StudyFragment;
import com.design.studyonline.utils.FragmentController;
import com.design.studyonline.view.bottomnavigation.BottomNavigationBar;
import com.design.studyonline.view.bottomnavigation.BottomNavigationItem;

import java.util.ArrayList;
import java.util.List;

/**
 * create by roy
 */
public class MainActivity extends AppCompatActivity implements BottomNavigationBar.OnTabSelectedListener {

    private BottomNavigationBar bottomNavigationBar;
    private FragmentController mController;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initSlide();
        initEvent();
    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawerLayout = ((DrawerLayout) findViewById(R.id.drawer_layout));
        bottomNavigationBar = (BottomNavigationBar) findViewById(R.id.bottom_navigation_bar);
    }


    private void initSlide() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        drawerLayout.setScrimColor(Color.parseColor("#66000000"));
        mDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        mDrawerToggle.syncState();
        drawerLayout.setDrawerListener(mDrawerToggle);
    }

    private void initEvent() {

        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new StudyFragment());
        fragments.add(new ResourceFragment());
        fragments.add(new CommunicationFragment());

        mController = FragmentController.getInstance(this, R.id.ui_container, fragments);
        mController.showFragments(0);

        bottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED);
        bottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);
        bottomNavigationBar.setBackgroundResource(R.color.white);
        bottomNavigationBar.setTabSelectedListener(this);
        List<BottomBean> beanList = BottomData.getBottoms();

        for (int i = 0; i < beanList.size(); i++) {
            BottomBean bean = beanList.get(i);
            bottomNavigationBar.addItem(new BottomNavigationItem(bean.getItemNormal(), bean.getName())
                    .setInactiveIconResource(bean.getItemPress())
                    .setInActiveColorResource(R.color.transparent_80)
                    .setActiveColorResource(R.color.colorPrimaryDark)).setTag(bean.getName());
        }
        bottomNavigationBar.initialise();
    }

    @Override
    public void onTabSelected(int position) {

        switch (position) {
            case 0:
                mController.showFragments(0);
                break;
            case 1:
                mController.showFragments(1);
                break;
            case 2:
                mController.showFragments(2);
                break;
        }

    }

    @Override
    public void onTabUnselected(int position) {

    }

    @Override
    public void onTabReselected(int position) {

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mController!=null){
            mController.onDestroy();
        }
    }
}
