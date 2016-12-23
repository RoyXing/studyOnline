package com.study.online.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.okhttp.Request;
import com.study.online.R;
import com.study.online.eventbusbean.CommunicationEventBean;
import com.study.online.view.TitleView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.BitmapCallback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by roy on 2016/12/23.
 * 社区详情类
 */

public class CommunicationDatialsActivity extends Activity implements View.OnClickListener {
    private TextView content;
    private ImageView img;
    TitleView mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_communication_datials);
        content = (TextView) findViewById(R.id.content);
        mToolbar = (TitleView) findViewById(R.id.toolbar);
        img = (ImageView) findViewById(R.id.img);
        mToolbar.setCustomTitle("帖子详情");
        mToolbar.isShowLeftImage(true);
        mToolbar.setLeftImageOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        OkHttpUtils.get().url("http://image61.360doc.com/DownloadImg/2013/05/1719/32405513_4.jpg").build().execute(new BitmapCallback() {
            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(Bitmap response) {
                img.setImageBitmap(response);
            }
        });

    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onEvent(CommunicationEventBean bean) {
        content.setText(bean.getBean().toString());
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
        }
    }
}
