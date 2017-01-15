package com.study.online.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;

import com.study.online.R;
import com.study.online.utils.ProgressGenerator;
import com.study.online.utils.ToastUtils;
import com.study.online.view.TitleView;
import com.study.online.view.button.ActionProcessButton;

/**
 * Created by roy on 2017/1/15.
 */

public class RegisterActivity extends Activity implements View.OnClickListener, ProgressGenerator.OnCompleteListener {

    private EditText register_nickname;
    private EditText register_account;
    private EditText register_password;
    private ActionProcessButton register;
    private TitleView mToolbar;
    private ProgressGenerator progressGenerator;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_layout);
        initView();
        initEvent();
    }

    private void initView() {
        register_nickname = (EditText) findViewById(R.id.register_nickname);
        register_account = (EditText) findViewById(R.id.register_account);
        register_password = (EditText) findViewById(R.id.register_password);
        register = (ActionProcessButton) findViewById(R.id.register);
        mToolbar = (TitleView) findViewById(R.id.toolbar);
    }

    private void initEvent() {
        mToolbar.setCustomTitle("注册");
        mToolbar.isShowLeftImage(true);
        mToolbar.setLeftImageOnClickListener(this);
        register.setOnClickListener(this);
        register.setMode(ActionProcessButton.Mode.ENDLESS);
        progressGenerator = new ProgressGenerator(this);
    }

    /**
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register:
                register_nickname.setEnabled(false);
                register_account.setEnabled(false);
                register_password.setEnabled(false);
                progressGenerator.register(this, register_nickname.getText().toString(), register_account.getText().toString(), register_password.getText().toString());
                break;
            case R.id.title_back:
                finish();
                break;
        }
    }

    @Override
    public void onComplete() {
        ToastUtils.show(this, "注册成功，请登陆");
        finish();
    }
}
