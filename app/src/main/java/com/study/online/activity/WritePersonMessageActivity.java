package com.study.online.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.InputType;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.study.online.R;
import com.study.online.bean.UserBean;
import com.study.online.config.Config;
import com.study.online.utils.DateTimePickDialogUtil;
import com.study.online.utils.DialogView;
import com.study.online.utils.FiledUtil;
import com.study.online.utils.JsonToBean;
import com.study.online.utils.ProgressGenerator;
import com.study.online.utils.SharedPreferencesDB;
import com.study.online.utils.ToastUtils;
import com.study.online.view.RoundImageView;
import com.study.online.view.TitleView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Calendar;

import okhttp3.Call;

/**
 * Created by 庞品 on 2017/1/22.
 */

public class WritePersonMessageActivity extends Activity implements View.OnClickListener, ProgressGenerator.OnCompleteListener {
    TitleView mToolbar;
    RoundImageView userImage;
    private RelativeLayout layName, laySex, layBirth, layStudyAccount, layStudyMajor, layStudyDepartment, laypassword;
    private TextView tvUserName, tvSex, tvBirth, tvStudyAccount, tvStudyMajor, tvStudyDepartment;
    ProgressGenerator progressGenerator;
    private String password;
    //popwindow 用户头像的  庞品
    private PopupWindow popupWindow;
    private View view, photo;//popwindow 的布局
    private LinearLayout layPhotoAction, layPhotoFile, layPhotoCanle;
    //更换头像使用
    public static final int P_NONE = 0;
    public static final int P_CAMERA = 1;
    public static final int P_ZOOM = 2;
    public static final int P_RESULT = 3;
    public static final String IMAGE_UNSPECIFIED = "image/*";
    private DialogView dialogViewPhoto, dialogViewPerson;
    //popwindow 用户性别的  庞品
    private PopupWindow popWindowSex;
    private View windowSex;
    private RelativeLayout relMan, relWoman;
    private ImageView imgM, imgW;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    String time = (String) msg.obj;
                    upPersonMessage("生日信息修改中...", "birthday", time);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_person_message);
        initView();
        try {
            setData();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void initView() {
        progressGenerator = new ProgressGenerator(this);
        mToolbar = (TitleView) findViewById(R.id.toolbar);
        mToolbar.setCustomTitle("个人中心");
        mToolbar.isShowLeftImage(true);
        mToolbar.setLeftImageOnClickListener(this);
        userImage = (RoundImageView) findViewById(R.id.userimg);
        layName = (RelativeLayout) findViewById(R.id.lay_unickname);
        laySex = (RelativeLayout) findViewById(R.id.lay_sex);
        layBirth = (RelativeLayout) findViewById(R.id.lay_birth);
        layStudyAccount = (RelativeLayout) findViewById(R.id.lay_study_accout);
        layStudyMajor = (RelativeLayout) findViewById(R.id.lay_study_major);
        layStudyDepartment = (RelativeLayout) findViewById(R.id.lay_study_department);
        laypassword = (RelativeLayout) findViewById(R.id.lay_password);

        tvUserName = (TextView) findViewById(R.id.tv_nickname);
        tvSex = (TextView) findViewById(R.id.tv_sex);
        tvBirth = (TextView) findViewById(R.id.tv_birth);
        tvStudyAccount = (TextView) findViewById(R.id.tv_study_account);
        tvStudyMajor = (TextView) findViewById(R.id.tv_study_major);
        tvStudyDepartment = (TextView) findViewById(R.id.tv_study_department);

        //监听
        userImage.setOnClickListener(this);
        layName.setOnClickListener(this);
        laySex.setOnClickListener(this);
        layBirth.setOnClickListener(this);
        layStudyAccount.setOnClickListener(this);
        layStudyMajor.setOnClickListener(this);
        layStudyDepartment.setOnClickListener(this);
        laypassword.setOnClickListener(this);
        dialogViewPerson = new DialogView(this);
        initTouPopwindow();
        initSexPopwindow();
    }

    /**
     * 初始化数据放置
     */
    private void setData() {
        //头像
        Picasso.with(this).load(SharedPreferencesDB.getInstance(WritePersonMessageActivity.this).getString("userimgae", "null")).placeholder(R.drawable.icon).error(R.drawable.icon).into(userImage);
        if (!SharedPreferencesDB.getInstance(WritePersonMessageActivity.this).getString("username", "").equals("null")) {
            tvUserName.setText(SharedPreferencesDB.getInstance(WritePersonMessageActivity.this).getString("username", ""));
        }
        if (!SharedPreferencesDB.getInstance(WritePersonMessageActivity.this).getString("userbirth", "").equals("")) {
            if (SharedPreferencesDB.getInstance(WritePersonMessageActivity.this).getString("userbirth", "").length() > 10) {
                tvBirth.setText(SharedPreferencesDB.getInstance(WritePersonMessageActivity.this).getString("userbirth", "").substring(0, 10));
            } else {
                tvBirth.setText("请设置你的生日");
            }
        } else {
            tvBirth.setText("请设置你的生日");
        }
        if (!SharedPreferencesDB.getInstance(WritePersonMessageActivity.this).getString("usersex", "").equals("")) {
            tvSex.setText(SharedPreferencesDB.getInstance(WritePersonMessageActivity.this).getString("usersex", ""));
        } else {
            tvSex.setText("请设置你的性别");
        }
        if (!SharedPreferencesDB.getInstance(WritePersonMessageActivity.this).getString("useraccout", "").equals("")) {
            tvStudyAccount.setText(SharedPreferencesDB.getInstance(WritePersonMessageActivity.this).getString("useraccout", ""));
        } else {
            tvStudyAccount.setText("请设置你的学号");
        }
        if (!SharedPreferencesDB.getInstance(WritePersonMessageActivity.this).getString("usermajor", "").equals("")) {
            tvStudyMajor.setText(SharedPreferencesDB.getInstance(WritePersonMessageActivity.this).getString("usermajor", ""));
        } else {
            tvStudyMajor.setText("请设置你的专业");
        }
        if (!SharedPreferencesDB.getInstance(WritePersonMessageActivity.this).getString("usercollege", "").equals("")) {
            tvStudyDepartment.setText(SharedPreferencesDB.getInstance(WritePersonMessageActivity.this).getString("usercollege", ""));
        } else {
            tvStudyDepartment.setText("请设置你的学院");
        }
    }

    /**
     * 初始化头像的
     */
    private void initTouPopwindow() {
        //popwindow 初始化,头像
        photo = findViewById(R.id.for_photo);
        view = LayoutInflater.from(this).inflate(R.layout.pop_for_photo, null);
        popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layPhotoAction = (LinearLayout) view.findViewById(R.id.lay_photo_action);
        layPhotoFile = (LinearLayout) view.findViewById(R.id.lay_photo_file);
        layPhotoCanle = (LinearLayout) view.findViewById(R.id.lay_photo_cancle);
        layPhotoCanle.setOnClickListener(this);
        layPhotoFile.setOnClickListener(this);
        layPhotoAction.setOnClickListener(this);
        dialogViewPhoto = new DialogView(this);
    }

    /**
     * 性别
     */
    private void initSexPopwindow() {
        //popwindow 初始化 ，性别
        windowSex = LayoutInflater.from(this).inflate(R.layout.pop_sex, null);
        popWindowSex = new PopupWindow(windowSex, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        relMan = (RelativeLayout) windowSex.findViewById(R.id.rel_sex_m);
        relWoman = (RelativeLayout) windowSex.findViewById(R.id.rel_sex_w);
        imgM = (ImageView) windowSex.findViewById(R.id.sex_img1);
        imgW = (ImageView) windowSex.findViewById(R.id.sex_img2);
        relWoman.setOnClickListener(this);
        relMan.setOnClickListener(this);

        //默认值显示
        String sex = SharedPreferencesDB.getInstance(this).getString("usersex", "0");
        if (sex.equals("男")) {
            imgM.setVisibility(View.VISIBLE);
        } else if (sex.equals("女")) {
            imgW.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 庞品
     * 显示popwindow界面  头像
     */
    private void showPopWindow() {
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
        } else {
            popupWindow.setBackgroundDrawable(new ColorDrawable());
            //popupWindow.setAnimationStyle(R.style.AnimationFade2);
            popupWindow.setOutsideTouchable(true);// 非popwindow区域可触摸
            popupWindow.setFocusable(true);//可获取焦点
            popupWindow.setTouchable(true);// 设置可触摸
            popupWindow.showAtLocation(photo, Gravity.BOTTOM, 0, 0);
        }
    }

    /**
     * 庞品  显示性别的popwindow
     */
    private void showPopWindowSex() {
        if (popWindowSex != null && popWindowSex.isShowing()) {
            popWindowSex.dismiss();
        } else {
            popWindowSex.setBackgroundDrawable(new ColorDrawable());
            //popupWindow.setAnimationStyle(R.style.AnimationFade2);
            popWindowSex.setOutsideTouchable(true);// 非popwindow区域可触摸
            popWindowSex.setFocusable(true);//可获取焦点
            popWindowSex.setTouchable(true);// 设置可触摸
            popWindowSex.showAsDropDown(tvSex, 10, 0);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_back:
                finish();
                break;
            //更换头像
            case R.id.userimg:
                showPopWindow();
                break;
            //拍照
            case R.id.lay_photo_action:
                takephotos();
                showPopWindow();
                break;
            //相册
            case R.id.lay_photo_file:
                choicephotos();
                showPopWindow();
                break;
            //取消
            case R.id.lay_photo_cancle:
                showPopWindow();
                break;
            //修改姓名
            case R.id.lay_unickname:
                editName(0, "请输入你的昵称");
                break;
            //修改性别
            case R.id.lay_sex:
                showPopWindowSex();

                break;
            //选择男
            case R.id.rel_sex_m:
                tvSex.setText("男");
                imgM.setVisibility(View.VISIBLE);
                imgW.setVisibility(View.INVISIBLE);
                upPersonMessage("性别信息修改中", "sex", "男");
                showPopWindowSex();
                break;
            //选择女
            case R.id.rel_sex_w:
                tvSex.setText("女");
                imgM.setVisibility(View.INVISIBLE);
                imgW.setVisibility(View.VISIBLE);
                upPersonMessage("性别信息修改中", "sex", "女");
                showPopWindowSex();
                break;
            //修改生日
            case R.id.lay_birth:
                choiceBirth();
                break;
            //修改学号
            case R.id.lay_study_accout:
                editName(1, "请输入你的学号");
                break;
            //修改专业
            case R.id.lay_study_major:
                editName(2, "请输入你的专业");
                break;
            //修改学院
            case R.id.lay_study_department:
                editName(3, "请输入你的学院");
                break;
            //修改密码
            case R.id.lay_password:
                resetPassword();
                break;
        }
    }

    /**
     * 修改生日
     */
    private void choiceBirth() {

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int moth = calendar.get(Calendar.MONTH);
        int dd = calendar.get(Calendar.DAY_OF_MONTH);

        String datetime = year + "年" + (moth + 1) + "月" + dd + "日" + "15:15";
        DateTimePickDialogUtil timePickDialogUtil = new DateTimePickDialogUtil(this, datetime, handler);
        timePickDialogUtil.dateTimePicKDialog(tvBirth);

    }

    /**
     * 修改名称的dialog
     *
     * @param tvName textView
     */
    private String name;//要修改的名称

    private void editName(final int positin, String hint) {
        final View view = getLayoutInflater().inflate(R.layout.dialog_custom_view_edittextname, null);
        final AlertDialog.Builder builder = new AlertDialog.Builder(WritePersonMessageActivity.this);
        builder.setView(view);
        final EditText editText = (EditText) view.findViewById(
                R.id.et_dialogCustomView_name);
        editText.setHint(hint);
        if (positin == 1) {
            editText.setInputType(InputType.TYPE_CLASS_NUMBER);
        }
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                name = editText.getText().toString().trim();
                if (!TextUtils.isEmpty(name)) {
                    switch (positin) {
                        case 0:
                            upPersonMessage("昵称修改中...", "userName", name);
                            break;
                        case 1:
                            upPersonMessage("学号信息修改中...", "studentId", name);
                            break;
                        case 2:
                            upPersonMessage("专业信息修改中...", "majoy", name);
                            break;
                        case 3:
                            upPersonMessage("学员信息修改中...", "college", name);
                            break;
                    }
                } else {
                    ToastUtils.show(WritePersonMessageActivity.this, "请输入要修改的内容");
                }
            }
        });
        builder.show();
    }

    /**
     * 修改密码的dialog
     */
    private void resetPassword() {
        final View view = getLayoutInflater().inflate(R.layout.dialog_custom_view_resetpassword, null);
        final AlertDialog.Builder builder = new AlertDialog.Builder(WritePersonMessageActivity.this);
        builder.setView(view);
        final EditText editTextold = (EditText) view.findViewById(
                R.id.et_old);
        final EditText editTextNewFirst = (EditText) view.findViewById(
                R.id.et_new_first);
        final EditText editTextNewLast = (EditText) view.findViewById(
                R.id.et_new_last);
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (!TextUtils.isEmpty(editTextold.getText().toString())) {
                    if (TextUtils.isEmpty(editTextNewFirst.getText().toString()) || TextUtils.isEmpty(editTextNewLast.getText().toString())) {
                        ToastUtils.show(WritePersonMessageActivity.this, "新密码不能为空");
                    } else if (editTextNewFirst.getText().toString().equals(editTextNewLast.getText().toString())) {
                        progressGenerator.login(WritePersonMessageActivity.this, SharedPreferencesDB.getInstance(WritePersonMessageActivity.this).getString("phone", ""), editTextold.getText().toString());
                        password = editTextNewFirst.getText().toString();
                    } else {
                        ToastUtils.show(WritePersonMessageActivity.this, "两次输入的新密码不一致");
                    }
                } else {
                    ToastUtils.show(WritePersonMessageActivity.this, "请输入你的原始密码");
                }
            }
        });
        builder.show();
    }

    /**
     * 上传个人休息修改
     */
    private void upPersonMessage(String message, final String type, final String content) {
        dialogViewPerson.show();
        dialogViewPerson.setMessage(message);
        OkHttpUtils.
                post().
                url(Config.UP_PERSON_MESSAGE).
                addParams("userId", SharedPreferencesDB.getInstance(this).getString("userid", "")).
                addParams(type, content).
                build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtils.show(WritePersonMessageActivity.this, "服务器错误");
                if (dialogViewPerson != null)
                    dialogViewPerson.close();

            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.optInt("code") == 10000 && jsonObject.optString("info").equals("success")) {
                        UserBean userBean = JsonToBean.getBean(jsonObject.optString("response").toString(), UserBean.class);
                        SharedPreferencesDB.getInstance(WritePersonMessageActivity.this).setString("username", userBean.getUserName());
                        SharedPreferencesDB.getInstance(WritePersonMessageActivity.this).setString("userimgae", userBean.getIcon());
                        SharedPreferencesDB.getInstance(WritePersonMessageActivity.this).setString("usersex", userBean.getSex());
                        SharedPreferencesDB.getInstance(WritePersonMessageActivity.this).setString("userbirth", userBean.getBirthday());
                        SharedPreferencesDB.getInstance(WritePersonMessageActivity.this).setString("useraccout", userBean.getStudentId());
                        SharedPreferencesDB.getInstance(WritePersonMessageActivity.this).setString("usermajor", userBean.getMajoy());
                        SharedPreferencesDB.getInstance(WritePersonMessageActivity.this).setString("usercollege", userBean.getCollege());
                        switch (type) {
                            //昵称
                            case "userName":
                                tvUserName.setText(content);
                                break;
                            //学号
                            case "studentId":
                                tvStudyAccount.setText(content);
                                break;
                            //专业
                            case "majoy":
                                tvStudyMajor.setText(content);
                                break;
                            //学院
                            case "college":
                                tvStudyDepartment.setText(content);
                                break;
                            //密码修改
                            case "password":
                                ToastUtils.show(WritePersonMessageActivity.this, "密码修改成功！");
                                break;
                            //头像上传
                            case "icon":
                                ToastUtils.show(WritePersonMessageActivity.this, "头像设置成功");
                                break;
                        }
                    } else {
                        ToastUtils.show(WritePersonMessageActivity.this, "信息修改失败！");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    ToastUtils.show(WritePersonMessageActivity.this, "信息修改失败！");
                }
                if (dialogViewPerson != null)
                    dialogViewPerson.close();
            }
        });
    }

    //一下是拍照所用
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == P_NONE)
            return;

		/* 拍照 */
        if (requestCode == P_CAMERA) {
            // 设置文件保存路径这里放在跟目录下
            File picture = new File(Environment.getExternalStorageDirectory()
                    + "/temp.jpg");
            startPhotoZoom(Uri.fromFile(picture));
        }

        if (data == null)
            return;

		/* 读取相册缩放图片 */
        if (requestCode == P_ZOOM) {
            startPhotoZoom(data.getData());
        }

		/* 处理结果 */
        if (requestCode == P_RESULT) {
            Bundle extras = data.getExtras();
            if (extras != null) {
                Bitmap photo = extras.getParcelable("data");
                userImage.setImageBitmap(photo);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                // (0 - 100)压缩文件
                photo.compress(Bitmap.CompressFormat.JPEG, 75, stream);

                String imageDir = Environment.getExternalStorageDirectory()
                        .getPath() + "/imageloader/Cache";
//                String imageName = SharedPreferencesDB.getInstance(this).getString("userId", "0")
//                        + ".jpg";
                File f = new File(imageDir, "study.jpg");
                FiledUtil.saveBitmap(photo, f);
                upPhoto(f);
            }
        }
    }

    /**
     * 上传头像
     *
     * @param f 参数头像文件
     */
    private void upPhoto(File f) {
        dialogViewPhoto.show();
        dialogViewPhoto.setMessage("头像上传中...");
        OkHttpUtils.post()
                .addFile("img", "userimg.jpg", f)
                .url(Config.UP_FILE)
                .params(null)
                .headers(null)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtils.show(WritePersonMessageActivity.this, "服务器错误");
                        if (dialogViewPhoto != null)
                            dialogViewPhoto.dismiss();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.optInt("code") == 10000) {
                                String url = jsonObject.getString("response");
                                ToastUtils.show(WritePersonMessageActivity.this, "头像上传成功");
                                upPersonMessage("头像信息修改中...", "icon", url);
                            } else {
                                ToastUtils.show(WritePersonMessageActivity.this, "头像上传失败，请重试");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        if (dialogViewPhoto != null)
                            dialogViewPhoto.dismiss();
                    }
                });
    }

    /**
     * 拍照
     */
    public void takephotos() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(
                Environment.getExternalStorageDirectory(), "temp.jpg")));
        startActivityForResult(intent, P_CAMERA);
    }

    /**
     * 选择图片
     */
    public void choicephotos() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                IMAGE_UNSPECIFIED);
        startActivityForResult(intent, P_ZOOM);
    }

    /**
     * 裁剪头像
     *
     * @param uri
     */
    public void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, IMAGE_UNSPECIFIED);
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, P_RESULT);
    }

    @Override
    public void onComplete() {
        upPersonMessage("密码修改中...", "password", password);
    }
}
