package com.study.online.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.study.online.R;
import com.study.online.config.Config;
import com.study.online.utils.DialogView;
import com.study.online.utils.JsonResult;
import com.study.online.utils.JsonUtils;
import com.study.online.utils.SharedPreferencesDB;
import com.study.online.utils.ToastUtils;
import com.study.online.view.TitleView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;



import okhttp3.Call;

/**
 * Created by roy on 2016/12/24.
 * 发帖界面
 */

public class WriteCommunicationActivity extends Activity implements View.OnClickListener {

    TitleView mToolbar;
    EditText edTitle, edContent;
    DialogView dialogView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_communication);
        initView();
    }

    private void initView() {
        mToolbar = (TitleView) findViewById(R.id.toolbar);
        edTitle = (EditText) findViewById(R.id.ed_title);
        edContent = (EditText) findViewById(R.id.ed_content);

        mToolbar.setCustomTitle("发帖");
        mToolbar.setRightImage(R.drawable.up);
        mToolbar.isShowLeftImage(true);
        mToolbar.isShowRightImage(true);
        mToolbar.setRightImageOnClickListener(this);
        mToolbar.setLeftImageOnClickListener(this);

    }

    /**
     * 进行数据上传
     */
    private void up() throws Exception {
        dialogView = new DialogView(this);
        dialogView.show();
        dialogView.setMessage("贴子上传中...");
        OkHttpUtils
                .post()
                .url(Config.ADD_TOPIC)
                .addParams("userId", SharedPreferencesDB.getInstance(this).getString("userid", ""))
                .addParams("content", new String(edContent.getText().toString().getBytes(), "UTF-8"))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtils.show(WriteCommunicationActivity.this,e.toString());
                        if (dialogView!=null)
                            dialogView.close();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        JsonResult<String> result = new JsonResult<String>();
                        try {
                            result = JsonUtils.getObject(response, new TypeToken<JsonResult<String>>() {
                            }.getType());
                            if (result.getCode()== 10000){
                                ToastUtils.show(WriteCommunicationActivity.this, "上传成功");
                                finish();
                            }
                        }catch (Exception e){

                        }

                        if (dialogView!=null)
                            dialogView.close();

                    }

                });
    }

    /**
     * 上传检测
     */
    private void check() {
//        if (TextUtils.isEmpty(edTitle.getText().toString())) {
//            Toast.makeText(this, "你的帖子还不完善，请输入标题", Toast.LENGTH_SHORT).show();
//        } else
        if (TextUtils.isEmpty(edContent.getText().toString())) {
            Toast.makeText(this, "你的帖子还不完善，请输入内容", Toast.LENGTH_SHORT).show();
        } else {
            try {
                up();
            }catch (Exception e){

            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.title_right:
                check();
                break;
        }
    }
}
