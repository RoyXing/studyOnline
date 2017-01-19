package com.study.online.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.study.online.R;
import com.study.online.utils.SharedPreferencesDB;

/**
 * Created by 庞品 on 2017/1/19.
 * 启动页
 */

public class StartActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout_start);
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    Thread.sleep(2000);//休眠3秒
                    if (SharedPreferencesDB.getInstance(StartActivity.this).getBoolean("isLogin", false)) {
                        Intent intent = new Intent(StartActivity.this, MainActivity.class);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(StartActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }
                    finish();
                }catch (Exception e){}

            }
        }.start();
    }
}
