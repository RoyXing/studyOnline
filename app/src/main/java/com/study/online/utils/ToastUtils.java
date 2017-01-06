package com.study.online.utils;
import android.content.Context;
import android.widget.Toast;

/**
 * @author:jeney
 * @date:2015/5/12
 * @todo:显示Toast的工具类
 */
public class ToastUtils {
    public static void show(Context context, String content) {
        if (null != context) {
            Toast toast = Toast.makeText(context, content, Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public static void show(Context context, int content) {
        if (null != context) {
            Toast toast = Toast.makeText(context, content, Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}

