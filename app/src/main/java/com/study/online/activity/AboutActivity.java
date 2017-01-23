package com.study.online.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.study.online.R;
import com.study.online.view.TitleView;

import org.w3c.dom.Text;

/**
 * Created by 庞品 on 2017/1/23.
 */

public class AboutActivity extends Activity implements View.OnClickListener {
    private TitleView toolbar;
    TextView tvContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        toolbar = (TitleView) findViewById(R.id.toolbar);
        toolbar.setCustomTitle("我的发帖");
        toolbar.isShowLeftImage(true);
        toolbar.setLeftImageOnClickListener(this);
        tvContent = (TextView) findViewById(R.id.tv_content);
        tvContent.setText("放入易学的相关简介即可");
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
