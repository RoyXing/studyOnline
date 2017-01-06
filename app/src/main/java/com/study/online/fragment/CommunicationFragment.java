package com.study.online.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;
import com.study.online.R;
import com.study.online.activity.WriteCommunicationActivity;
import com.study.online.adapter.CommunicationAdapter;
import com.study.online.bean.TopicBean;
import com.study.online.config.Config;
import com.study.online.eventbusbean.CommunicationEventBean;
import com.study.online.utils.DialogView;
import com.study.online.utils.JsonResult;
import com.study.online.utils.JsonUtils;
import com.study.online.utils.TimeUtils;
import com.study.online.utils.ToastUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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
    DialogView dialogView;
    int page = 0;

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
        adapter = new CommunicationAdapter(getActivity(), 0, list);
        listView.setAdapter(adapter);
        listView.addFooterView(footview);
        listener();
        getList();
        EventBus.getDefault().register(this);
    }

    private void listener() {
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
                        JsonResult<List<TopicBean>> result = new JsonResult<List<TopicBean>>();
                        try {
                            result = JsonUtils.getObject(response, new TypeToken<JsonResult<List<TopicBean>>>() {
                            }.getType());
                            if (result.getCode() == 10000) {
                                listView.removeFooterView(footview);
                                if (page == 0)
                                    list.clear();
                                if (result.getResponse().size() >= 20) {
                                    page++;
                                    listView.addFooterView(footview);
                                } else {
                                    listView.removeFooterView(footview);
                                }
                                list.addAll(result.getResponse());
                                adapter.notifyDataSetChanged();
                            }
                        } catch (Exception e) {

                        }
                        refreshLayout.setRefreshing(false);
                        if (dialogView != null)
                            dialogView.close();

                    }

                });
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(String[] data) {
        for (int i=0;i<list.size();i++){
            if (list.get(i).getTopicId().equals(data[0])){
                list.get(i).setCommentNum(data[1]);
                adapter.notifyDataSetChanged();
                break;
            }
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
