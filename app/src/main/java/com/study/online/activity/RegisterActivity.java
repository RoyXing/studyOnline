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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
//                register_nickname.setEnabled(false);
//                register_account.setEnabled(false);
//                register_password.setEnabled(false);
                String password = register_password.getText().toString();
                if (isRightPassword(password)) {
                    progressGenerator.register(this, register_nickname.getText().toString(), register_account.getText().toString(), password);
                } else {
                    ToastUtils.show(this, "密码应同时包含数字和字母并且长度为8-15位");
                }

                break;
            case R.id.title_back:
                finish();
                break;
        }
    }


    /**
     * 验证输入密码条件(字符与数据同时出现)
     *
     * @param str 待验证的字符串
     * @return 如果是符合格式的字符串, 返回 <b>true </b>,否则为 <b>false </b>
     */
    public static boolean isRightPassword(String str) {
        String regex = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,15}$";
        return match(regex, str);
    }

    /**
     * @param regex 正则表达式字符串
     * @param str   要匹配的字符串
     * @return 如果str 符合 regex的正则表达式格式,返回true, 否则返回 false;
     */
    private static boolean match(String regex, String str) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }


    @Override
    public void onComplete() {
        ToastUtils.show(this, "注册成功，请登陆");
        finish();
    }
}
