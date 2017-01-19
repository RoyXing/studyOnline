package com.study.online.utils;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.google.gson.reflect.TypeToken;
import com.study.online.App;
import com.study.online.bean.UserBean;
import com.study.online.config.Config;
import com.study.online.view.button.ProcessButton;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

import okhttp3.Call;

public class ProgressGenerator {

    public interface OnCompleteListener {

        void onComplete();
    }

    private OnCompleteListener mListener;
    private int mProgress;

    public ProgressGenerator(OnCompleteListener listener) {
        mListener = listener;
    }

    public void login(final Context context, String userName, String password) {
        OkHttpUtils
                .post()
                .url(Config.LOGIN)
                .addParams("phone", userName)
                .addParams("password", password)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.optInt("code") == 10000 && jsonObject.optString("info").equals("success")) {
                                UserBean userBean = JsonToBean.getBean(jsonObject.optString("response").toString(), UserBean.class);
                                SharedPreferencesDB.getInstance(context).setString("userid", userBean.getUserId());
                                SharedPreferencesDB.getInstance(context).setString("username", userBean.getUserName());
                                SharedPreferencesDB.getInstance(context).setString("userimgae", userBean.getIcon());
                                mListener.onComplete();
                            } else {
                                ToastUtils.show(context, "登录失败！");
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                            ToastUtils.show(context, "账号或密码出错");
                        }
                    }
                });
    }

    /**
     * phone,password,userName
     *
     * @param context
     * @param nickName
     * @param userName
     * @param password
     */
    public void register(final Context context, String nickName, String userName, String password) {

        OkHttpUtils.post()
                .url(Config.RIGISTER)
                .addParams("userName", nickName)
                .addParams("phone", userName)
                .addParams("password", password)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                Log.e("roy", response.toString());
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.optInt("code") == 10000 && jsonObject.optString("info").equals("success")) {
                        mListener.onComplete();
                    } else {
                        ToastUtils.show(context, "注册失败！");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void start(final ProcessButton button) {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mProgress += 10;
                button.setProgress(mProgress);
                if (mProgress < 100) {
                    handler.postDelayed(this, generateDelay());
                } else {
                    mListener.onComplete();
                }
            }
        }, generateDelay());
    }

    private Random random = new Random();

    private int generateDelay() {
        return random.nextInt(1000);
    }
}
