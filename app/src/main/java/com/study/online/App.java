package com.study.online;

import android.app.Application;

import com.study.online.bean.UserBean;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

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
    private void init() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                //其他配置
                .build();

        OkHttpUtils.initClient(okHttpClient);
    }
}
