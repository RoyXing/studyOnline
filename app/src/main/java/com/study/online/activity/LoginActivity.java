package com.study.online.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import com.google.gson.reflect.TypeToken;
import com.study.online.R;
import com.study.online.bean.UserBean;
import com.study.online.config.Config;
import com.study.online.utils.DialogView;
import com.study.online.utils.JsonResult;
import com.study.online.utils.JsonUtils;
import com.study.online.utils.ProgressGenerator;
import com.study.online.utils.SharedPreferencesDB;
import com.study.online.utils.ToastUtils;
import com.study.online.view.TitleView;
import com.study.online.view.button.ActionProcessButton;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;
import okhttp3.Request;


public class LoginActivity extends Activity implements ProgressGenerator.OnCompleteListener, View.OnClickListener {

    EditText editEmail;
    EditText editPassword;
    ProgressGenerator progressGenerator;
    ActionProcessButton btnSignIn;
    TitleView mToolbar;
    DialogView dialogView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout_login);

        initView();
        initEvent();
    }

    private void initView() {
        progressGenerator = new ProgressGenerator(this);
        editEmail = (EditText) findViewById(R.id.editEmail);
        editPassword = (EditText) findViewById(R.id.editPassword);
        btnSignIn = (ActionProcessButton) findViewById(R.id.btnSignIn);
        mToolbar = (TitleView) findViewById(R.id.toolbar);
        editEmail.setSelection(editEmail.getText().length());
        editPassword.setSelection(editPassword.getText().length());
    }

    private void initEvent() {
        mToolbar.setCustomTitle("登录");
        mToolbar.isShowLeftImage(false);
        mToolbar.setLeftImageOnClickListener(this);
        btnSignIn.setMode(ActionProcessButton.Mode.ENDLESS);
//            btnSignIn.setMode(ActionProcessButton.Mode.PROGRESS);
        btnSignIn.setOnClickListener(this);
    }

    private void login() {
        dialogView = new DialogView(this);
        dialogView.show();
        dialogView.setMessage("正在登陆");
        OkHttpUtils
                .post()
                .url(Config.LOGIN)
                .addParams("phone", editEmail.getText().toString())
                .addParams("password", editPassword.getText().toString())
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        if (dialogView != null)
                            dialogView.close();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        JsonResult<UserBean> result = new JsonResult<UserBean>();
                        try {
                            result = JsonUtils.getObject(response, new TypeToken<JsonResult<UserBean>>() {
                            }.getType());
                            if (result.getCode() == 10000) {
                                SharedPreferencesDB.getInstance(LoginActivity.this).setString("userid",result.getResponse().getUserId());
                                onComplete();
                                ToastUtils.show(LoginActivity.this, "登录成功");
                            } else {
                                ToastUtils.show(LoginActivity.this, "登录失败");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            ToastUtils.show(LoginActivity.this, "账号或密码出错");
                        }


                        if (dialogView != null)
                            dialogView.close();
                    }
                });
    }


    @Override
    public void onComplete() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
        //Toast.makeText(this, "登录成功", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSignIn:
                login();
//                progressGenerator.start(btnSignIn);
//                btnSignIn.setEnabled(false);
//                editEmail.setEnabled(false);
//                editPassword.setEnabled(false);
                break;
            case R.id.title_back:
                finish();
                break;
        }
    }
}
