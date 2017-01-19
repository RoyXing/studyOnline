package com.study.online.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.squareup.picasso.Picasso;
import com.study.online.App;
import com.study.online.R;
import com.study.online.adapter.SlideMenuAdapter;
import com.study.online.bean.BottomBean;
import com.study.online.bean.SlideMenuItem;
import com.study.online.data.BottomData;
import com.study.online.fragment.CommunicationFragment;
import com.study.online.fragment.ResourceFragment;
import com.study.online.fragment.StudyFragment;
import com.study.online.utils.FragmentController;
import com.study.online.utils.SharedPreferencesDB;
import com.study.online.utils.ToastUtils;
import com.study.online.view.RoundImageView;
import com.study.online.view.TitleView;
import com.study.online.view.bottomnavigation.BottomNavigationBar;
import com.study.online.view.bottomnavigation.BottomNavigationItem;
import com.study.online.view.button.ActionProcessButton;

import java.util.ArrayList;
import java.util.List;

/**
 * create by roy
 */
public class MainActivity extends AppCompatActivity implements BottomNavigationBar.OnTabSelectedListener, View.OnClickListener {

    private BottomNavigationBar bottomNavigationBar;
    private FragmentController mController;
    private TitleView toolbar;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    //侧滑部分
    private RoundImageView slide_head_image;
    private TextView slide_head_name;
    private ListView slide_listView;
    private ActionProcessButton slide_btnExit;
    private RelativeLayout slide_head;
    private SlideMenuAdapter adapter;
    private List<SlideMenuItem> slideMenuItemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initSlide();
        initEvent();
        listener();
    }

    private void initView() {
        toolbar = (TitleView) findViewById(R.id.toolbar);
        drawerLayout = ((DrawerLayout) findViewById(R.id.drawer_layout));
        bottomNavigationBar = (BottomNavigationBar) findViewById(R.id.bottom_navigation_bar);
        slide_head_image = (RoundImageView) findViewById(R.id.slide_userimg);
        slide_head_name = (TextView) findViewById(R.id.slid_username);
        slide_listView = (ListView) findViewById(R.id.slide_listview);
        slide_btnExit = (ActionProcessButton) findViewById(R.id.slide_btn_exit);
        slide_head = (RelativeLayout) findViewById(R.id.slid_head);
        Picasso.with(this).load(SharedPreferencesDB.getInstance(this).getString("userimgae", "")).placeholder(R.drawable.icon).error(R.drawable.icon).into(slide_head_image);
        slide_head_name.setText(SharedPreferencesDB.getInstance(this).getString("username", "易学"));
    }


    private void initSlide() {
        setSupportActionBar(toolbar);
        toolbar.setTitle(getString(R.string.app_name));
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
        dataForlist();
    }

    private void listener() {
        slide_head_image.setOnClickListener(this);
        slide_btnExit.setOnClickListener(this);
        slide_head.setOnClickListener(this);
        //侧滑菜单
        slide_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ToastUtils.show(MainActivity.this, slideMenuItemList.get(position).getItme_text());
            }
        });
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
        if (mController != null) {
            mController.onDestroy();
        }
    }


    @Override
    public void onBackPressed() {
        //当侧滑打开的时候，返回键，先关闭侧滑
        if (slide_listView.isShown()) {
            drawerLayout.closeDrawers();
        } else {
            super.onBackPressed();
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //切换头像
            case R.id.slide_userimg:
                ToastUtils.show(MainActivity.this, "切换头像");
                break;
            //退出当前账号
            case R.id.slide_btn_exit:
                ToastUtils.show(MainActivity.this, "退出账号");
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                SharedPreferencesDB.getInstance(MainActivity.this).setBoolean("isLogin", false);
                finish();
                break;
        }
    }

    /**
     * 庞品
     * 这个方法是给侧边menu集合赋值
     */
    private void dataForlist() {
        slideMenuItemList = new ArrayList<>();
        SlideMenuItem p1 = new SlideMenuItem("我的发帖", R.drawable.write_me);
        SlideMenuItem p2 = new SlideMenuItem("关于我们", R.drawable.us);
        // PersonCenterItem p3 = new PersonCenterItem("我的账户", R.drawable.person_listview_mywallet);
        SlideMenuItem p4 = new SlideMenuItem("还有什么", R.drawable.us);
        SlideMenuItem p5 = new SlideMenuItem("意见反馈", R.drawable.us);
        slideMenuItemList.add(p1);
        slideMenuItemList.add(p2);
        //personCenterItemList.add(p3);
        slideMenuItemList.add(p4);
        slideMenuItemList.add(p5);
        adapter = new SlideMenuAdapter(this, R.layout.item_person_center, slideMenuItemList);
        slide_listView.setAdapter(adapter);
    }
}
