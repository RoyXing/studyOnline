package com.study.online.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.study.online.R;
import com.study.online.fragment.CommunicationFragment;
import com.study.online.view.TitleView;

/**
 * Created by 庞品 on 2017/1/23.
 * 我的发帖及与我相关
 */

public class MyWriteAndMyCommitActivity extends AppCompatActivity implements View.OnClickListener {
    TitleView mToolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mywrite_mycommit);
        String position = getIntent().getStringExtra("my");
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment);
        if (fragment == null) {
            fragment = new CommunicationFragment();
            Bundle bundle=new Bundle();
            bundle.putInt("how",Integer.parseInt(position));
            fragment.setArguments(bundle);
            fm.beginTransaction().add(R.id.fragment, fragment).commit();
        }
        initView(position);
    }

    private void initView(String position) {
        mToolbar = (TitleView) findViewById(R.id.toolbar);
        mToolbar.isShowLeftImage(true);
        mToolbar.setLeftImageOnClickListener(this);
        if (position.equals("0")) {
            mToolbar.setCustomTitle("我的发帖");
        } else if (position.equals("1")) {
            mToolbar.setCustomTitle("与我相关");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_back:
                finish();
                break;
        }
    }
}
