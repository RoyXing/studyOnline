package com.study.online.utils;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import com.study.online.R;


public class DialogView extends Dialog {

    public static final int DEFAULT_STYLE = R.style.DialogTheme;

    Context context;
    private TextView textView;

    public DialogView(Context context) {
        this(context, DEFAULT_STYLE);
    }

    public DialogView(Context context, int style) {
        super(context, style);
        this.context = context;
        this.setCanceledOnTouchOutside(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_loading);

        textView = (TextView) findViewById(R.id.text_message);
    }

    /**
     * 调改方法前 请先调show方法
     *
     * @param message
     */
    public void setMessage(String message) {
        textView.setText(message);
    }

    public void setMessage(int resId) {
        textView.setText(resId);
    }

    public void close() {
        if (this != null && this.isShowing()) {
            dismiss();
        }
    }
}
