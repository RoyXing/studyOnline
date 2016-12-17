package com.design.studyonline.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.design.studyonline.R;
import com.design.studyonline.utils.ProgressGenerator;
import com.design.studyonline.view.TitleView;
import com.design.studyonline.view.button.ActionProcessButton;


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
        mToolbar.setLeftImageOnClickListener(this);
        btnSignIn.setMode(ActionProcessButton.Mode.ENDLESS);
//            btnSignIn.setMode(ActionProcessButton.Mode.PROGRESS);
        btnSignIn.setOnClickListener(this);
    }

    @Override
    public void onComplete() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
        Toast.makeText(this, "登录成功", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSignIn:
                progressGenerator.start(btnSignIn);
                btnSignIn.setEnabled(false);
                editEmail.setEnabled(false);
                editPassword.setEnabled(false);
                break;
            case R.id.title_back:
                finish();
                break;
        }
    }
}
