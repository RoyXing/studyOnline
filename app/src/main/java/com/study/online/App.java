package com.study.online;

import android.app.Application;

import com.zhy.http.okhttp.OkHttpUtils;

import java.io.IOException;

/**
 * Created by roy on 2016/12/17.
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    /**
     * 网络请求封装，okhttputils  张鸿洋的
     * 使用方法请看以下网页介绍
     * http://blog.csdn.net/lmj623565791/article/details/49734867/
     */
    private void init(){
        try {
            OkHttpUtils
                    .getInstance()
                    .setCertificates(getAssets().open("aaa.cer"),
                            getAssets().open("server.cer"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
