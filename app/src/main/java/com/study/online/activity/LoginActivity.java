package com.study.online.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.study.online.R;
import com.study.online.utils.ProgressGenerator;
import com.study.online.utils.SharedPreferencesDB;
import com.study.online.utils.ToastUtils;
import com.study.online.view.TitleView;
import com.study.online.view.button.ActionProcessButton;


public class LoginActivity extends Activity implements ProgressGenerator.OnCompleteListener, View.OnClickListener {

    EditText editEmail;
    EditText editPassword;
    ProgressGenerator progressGenerator;
    ActionProcessButton btnSignIn;
    TitleView mToolbar;

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
        mToolbar.setRightText("注册");
        mToolbar.isShowRightText(true);
        mToolbar.setRightTextOnClickListener(this);
        mToolbar.setLeftImageOnClickListener(this);
        btnSignIn.setMode(ActionProcessButton.Mode.ENDLESS);
//            btnSignIn.setMode(ActionProcessButton.Mode.PROGRESS);
        btnSignIn.setOnClickListener(this);
    }

    @Override
    public void onComplete() {
        SharedPreferencesDB.getInstance(this).setBoolean("isLogin", true);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSignIn:
                if (TextUtils.isEmpty(editEmail.getText().toString())) {
                    ToastUtils.show(LoginActivity.this, "请输入你的账号");
                } else if (TextUtils.isEmpty(editPassword.getText().toString())) {
                    ToastUtils.show(LoginActivity.this, "请输入你的密码");
                } else {
//                    btnSignIn.setEnabled(false);
//                    editEmail.setEnabled(false);
//                    editPassword.setEnabled(false);
                    progressGenerator.login(this, editEmail.getText().toString(), editPassword.getText().toString());
                }
                break;
            case R.id.title_back:
                finish();
                break;
            case R.id.title_right_text:
                Intent intent = new Intent(this, RegisterActivity.class);
                startActivity(intent);
                break;
        }
    }
}
