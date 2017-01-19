package com.study.online.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;
import com.study.online.R;
import com.study.online.adapter.CommunicationCommitAdapter;
import com.study.online.bean.TopicBean;
import com.study.online.bean.TopicCommitBean;
import com.study.online.bean.TopicCommitLocalBean;
import com.study.online.config.Config;
import com.study.online.utils.DialogView;
import com.study.online.utils.JsonResult;
import com.study.online.utils.JsonUtils;
import com.study.online.utils.SharedPreferencesDB;
import com.study.online.utils.TimeUtils;
import com.study.online.utils.ToastUtils;
import com.study.online.view.ExpandListview;
import com.study.online.view.RoundImageView;
import com.study.online.view.TitleView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;


import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;


/**
 * Created by roy on 2016/12/23.
 * 社区详情类
 */

public class CommunicationDatialsActivity extends Activity implements View.OnClickListener {
    TitleView mToolbar;
    RoundImageView userImg;
    TextView userName, articleTitle, articleContent, articleCommit, articleTime;
    ExpandListview expandListview;
    ScrollView scrollView;
    EditText edCommitContent;
    Button btnCommit;
    View footview;
    CardView head;
    List<TopicCommitBean> list;
    CommunicationCommitAdapter adapter;
    TopicCommitLocalBean topicBean;
    DialogView dialogView;
    int page = 0;
    int pageCache=0;//请求页缓存，用来去出重复数据
    String commitNumber = "";//传给列表界面的评论数
    boolean write = false;//用判定是否是提交时刷新
    TopicBean topicBeanf;//上个界面传过来的数据

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_communication_datials);
        initView();
        topicBean = new TopicCommitLocalBean();
        topicBean.setUserId(SharedPreferencesDB.getInstance(this).getString("userid", ""));
        topicBeanf = (TopicBean) getIntent().getBundleExtra("intent").getSerializable("bean");
        onEvent(topicBeanf);

    }

    private void initView() {
        mToolbar = (TitleView) findViewById(R.id.toolbar);
        mToolbar.setCustomTitle("帖子详情");
        mToolbar.isShowLeftImage(true);
        mToolbar.setLeftImageOnClickListener(this);
        articleCommit = (TextView) findViewById(R.id.article_commit);
        articleTitle = (TextView) findViewById(R.id.article_title);
        articleContent = (TextView) findViewById(R.id.article_content);
        userName = (TextView) findViewById(R.id.username);
        articleTime = (TextView) findViewById(R.id.article_time);
        userImg = (RoundImageView) findViewById(R.id.userimg);
        expandListview = (ExpandListview) findViewById(R.id.exlistview);
        scrollView = (ScrollView) findViewById(R.id.scrollView);
        edCommitContent = (EditText) findViewById(R.id.ed_commit);
        btnCommit = (Button) findViewById(R.id.btn_commit);
        head = (CardView) findViewById(R.id.cardview);
        footview = LayoutInflater.from(this).inflate(R.layout.footview, null);
        head.setOnClickListener(this);
        btnCommit.setOnClickListener(this);
        list = new ArrayList<>();
        dialogView = new DialogView(this);
        adapter = new CommunicationCommitAdapter(this, 0, list);
        expandListview.addFooterView(footview);
        expandListview.setAdapter(adapter);
        expandListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                topicBean.setParentId(list.get(i).getCommentId());
                edCommitContent.setText("");
                edCommitContent.setHint("回复" + (i + 1) + "楼");
                InputMethodManager imm = (InputMethodManager) CommunicationDatialsActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                edCommitContent.setFocusable(true);
            }
        });
        //加载更多
        footview.findViewById(R.id.cardview_foot).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDatials();
            }
        });
    }

    /**
     * 列表界面传递过来的数据
     * @param bean
     */
    public void onEvent(TopicBean bean) {
        if (bean != null) {
            userName.setText(bean.getUserName());
            //articleTitle.setText(bean.getBean().getTitle());
            articleTitle.setVisibility(View.GONE);
            articleContent.setText(bean.getContent());
            articleCommit.setText("评论数：" + bean.getCommentNum());
            articleTime.setText(TimeUtils.longToString(bean.getCreateTime()));
            Picasso.with(this).load(bean.getIcon()).placeholder(R.drawable.icon).error(R.drawable.icon).into(userImg);
            topicBean.setTopicId(bean.getTopicId());
            topicBean.setParentId("");
            getDatials();
        }
    }

    /**
     * 获取之前的评论
     */
    private void getDatials() {
        dialogView.show();
        dialogView.setMessage("评论数据加载中...");
        OkHttpUtils
                .post()
                .url(Config.TOPIC + topicBean.getTopicId())
                .addParams("page", page + "")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtils.show(CommunicationDatialsActivity.this, e.toString());
                        if (dialogView != null)
                            dialogView.close();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        JsonResult<TopicBean> result = new JsonResult<TopicBean>();
                        try {
                            result = JsonUtils.getObject(response, new TypeToken<JsonResult<TopicBean>>() {
                            }.getType());
                            if (result.getCode() == 10000) {
                                //如果评论的数据多余当前显示的数据，只让上面的评论数+1
                                setHeadData(result);
                                if (write) {//如果是评论时刷新数据
                                    write = false;
                                    if (list.size() >= 10) {//超过10条，不予理会
                                        expandListview.removeFooterView(footview);
                                        expandListview.addFooterView(footview);
                                    } else {//未满10条，刷新界面，显示那条数据
                                        listDataSet(result);
                                    }
                                } else {//不是的时候走正常的路
                                    listDataSet(result);
                                }
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        if (dialogView != null)
                            dialogView.close();
                        pageCache=page;
                    }

                });
    }

    /**
     * 列表数据更新
     *
     * @param result
     */
    private void listDataSet(JsonResult<TopicBean> result) {
        expandListview.removeFooterView(footview);
        ToastUtils.show(CommunicationDatialsActivity.this, "评论加载成功");
        if (page == 0)
            list.clear();
        //去除重复的数据，在评论数据未满整数时
        if (list.size()>0&&(page+1)*10>list.size()){
            int size=list.size()%10;
            for (int i=size-1;i>=0;i--){
                list.remove(page*10+i);
            }
        }
        if (result.getResponse().getCommentList().size() >= 10) {
            expandListview.addFooterView(footview);
            page++;
        } else {
            expandListview.removeFooterView(footview);
        }
        list.addAll(result.getResponse().getCommentList());
        adapter.notifyDataSetChanged();
        //if (page == 0)
            //scrollView.smoothScrollTo(0, 0);
    }

    //对头部的数据进行更新
    private void setHeadData(JsonResult<TopicBean> result) {
        userName.setText(result.getResponse().getUserName());
        //articleTitle.setText(bean.getBean().getTitle());
        articleTitle.setVisibility(View.GONE);
        articleContent.setText(result.getResponse().getContent());
        articleCommit.setText("评论数：" + result.getResponse().getCommentNum());
        articleTime.setText(TimeUtils.longToString(result.getResponse().getCreateTime()));
        commitNumber = result.getResponse().getCommentNum();
    }

    /**
     * 提交评论
     */
    private void writeCommit() {
        dialogView.show();
        dialogView.setMessage("评论发送中...");
        OkHttpUtils
                .post()
                .url(Config.ADD_COMMENT_TOPIC)
                .addParams("parentId", topicBean.getParentId())
                .addParams("content", topicBean.getContent())
                .addParams("topicId", topicBean.getTopicId())
                .addParams("userId", topicBean.getUserId())
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtils.show(CommunicationDatialsActivity.this, e.toString());
                        if (dialogView != null)
                            dialogView.close();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        JsonResult<String> result;
                        try {
                            result = JsonUtils.getObject(response, new TypeToken<JsonResult<String>>() {
                            }.getType());
                            if (result.getCode() == 10000) {
                                write = true;
                                ToastUtils.show(CommunicationDatialsActivity.this, "评论成功");
                                if (list.size() < 10) {//小于10条的时候，直接刷新
                                    page = 0;
                                }
                                getDatials();
                            }

                        } catch (Exception e) {

                        }
                        if (dialogView != null)
                            dialogView.close();
                    }

                });
    }

    @Override
    public void onBackPressed() {//返回时把当前的评论数返回，用于列表界面的刷新
        returnIntent();
        finish();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                returnIntent();
                finish();
                break;
            case R.id.btn_commit:
                if (TextUtils.isEmpty(edCommitContent.getText().toString())) {
                    Toast.makeText(CommunicationDatialsActivity.this, "请输入评论的内容", Toast.LENGTH_SHORT).show();
                    return;
                }
                topicBean.setContent(edCommitContent.getText().toString());
                writeCommit();
                edCommitContent.setText("");
                edCommitContent.setHint("期待你的评论");
                topicBean.setParentId("");
                InputMethodManager imm = (InputMethodManager) CommunicationDatialsActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(edCommitContent.getWindowToken(), 0);
                break;
            case R.id.cardview:
                edCommitContent.setText("");
                edCommitContent.setHint("期待你的评论");
                topicBean.setParentId("");
                InputMethodManager immm = (InputMethodManager) CommunicationDatialsActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
                immm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                edCommitContent.setFocusable(true);
                break;
        }
    }

    /**
     * 评论次数数据返回
     */
    private void returnIntent() {
        Intent intent = new Intent();
        intent.putExtra("number", commitNumber);
        setResult(Activity.RESULT_OK, intent);
    }
}
