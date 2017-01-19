package com.study.online.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.study.online.R;
import com.study.online.activity.CommunicationDatialsActivity;
import com.study.online.activity.WriteCommunicationActivity;
import com.study.online.adapter.CommunicationAdapter;
import com.study.online.bean.TopicBean;
import com.study.online.config.Config;
import com.study.online.utils.DialogView;
import com.study.online.utils.JsonToBean;
import com.study.online.utils.ToastUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;


import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;


/**
 * Created by roy on 2016/12/16.
 * 社区
 */

public class CommunicationFragment extends BaseFragment {

    private View mView;
    private SwipeRefreshLayout refreshLayout;
    private ListView listView;
    private View footview;
    private ImageView write;
    private List<TopicBean> list;
    private CommunicationAdapter adapter;
    public DialogView dialogView;
    int page = 0;
    int positon;//点击的位置

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_layout_commincation, null);
        return mView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refresh);
        listView = (ListView) view.findViewById(R.id.listview_communitcation);
        write = (ImageView) view.findViewById(R.id.img_write);
        footview = LayoutInflater.from(getActivity()).inflate(R.layout.footview, null);
        list = new ArrayList<>();
        adapter = new CommunicationAdapter(getContext(), 0, list);
        listView.setAdapter(adapter);
        listView.addFooterView(footview);
        listener();
        getList();
    }

    private void listener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                positon = position;
                Intent intent = new Intent(getActivity(), CommunicationDatialsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("bean", list.get(position));
                intent.putExtra("intent", bundle);
                startActivityForResult(intent, 1000);
            }
        });
        //刷新
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 0;
                getList();

            }
        });
        //加载更多
        footview.findViewById(R.id.cardview_foot).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getList();
            }
        });
        //发帖
        write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), WriteCommunicationActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * 进行数据上传
     */
    private void getList() {
        dialogView = new DialogView(getActivity());
        dialogView.show();
        dialogView.setMessage("贴子加载中...");
        OkHttpUtils
                .post()
                .url(Config.TOPIC_LIST)
                .addParams("page", page + "")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtils.show(getActivity(), e.toString());
                        if (dialogView != null)
                            dialogView.close();
                        refreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.optInt("code") == 10000 && jsonObject.optString("info").equals("success")) {
                                List<TopicBean> topicBeanList = JsonToBean.getBeans(jsonObject.opt("response").toString(), TopicBean.class);
                                listView.removeFooterView(footview);
                                if (page == 0)
                                    list.clear();
                                if (topicBeanList.size() >= 10) {
                                    page++;
                                    listView.addFooterView(footview);
                                } else {
                                    listView.removeFooterView(footview);
                                }
                                list.addAll(topicBeanList);
                                adapter.notifyDataSetChanged();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        refreshLayout.setRefreshing(false);
                        if (dialogView != null)
                            dialogView.close();

                    }

                });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000 && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                list.get(positon).setCommentNum(data.getStringExtra("number"));
                adapter.notifyDataSetChanged();
            }
        }

    }


}
